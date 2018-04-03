package cp.html;
import java_cup.runtime.Symbol;
import cp.SymTable.SymTable;
import json.JsonResult;
import Reporter.Reporter;

%%

%{
    StringBuffer string = new StringBuffer();
    StringBuffer crcode = new StringBuffer();

    SymTable table = new SymTable();

    JsonResult jsonResult = new JsonResult();
    
    private Symbol symbol(int type, Object value, boolean goBack) {
        return new Symbol(type, yyline, yycolumn + (goBack ? 1 : 0), value);
    }

    private Symbol symbol(int type, Object value) {
        return symbol(type, value, false);
    }

    public void setJsonResult(JsonResult jsonResult){
        this.jsonResult = jsonResult;
    }

    private HtmlCreator htmlCreator = new HtmlCreator();
 
    public void setHtmlCreator(HtmlCreator htmlCreator){
        this.htmlCreator = htmlCreator;
    }

    private String compileCP(String code){

        cp.Compiler crCompiler = new cp.Compiler(table, jsonResult, yyline, yycolumn);

        String r = crCompiler.Analyze(code); 
        
        table = crCompiler.getSymTable();

        return r;
    }

    private void addProperty(String name, String value){
        try {
            htmlCreator.addProperty(name, value);
        } catch (Exception ex) {
            Reporter.addSemanticError(ex.getMessage(), yyline + 1, yycolumn);
        }
    }

    String currentPropertyName = "";
%}

%cup
%class scanner
%public
%line
%char
%column
%full
%ignorecase  

//EXPRESIONES REGULARES
//EXPNUMBER             = [\-]?[0-9]+[\. 0-9+]?[(e|E)(+|-)?0-9+]?
EXPLINE                 = [\ \n]
EXPSPACE                = [\r|\n|\r\n] | [ \t\f]

//OPENING
HTML                    = "html"
HEAD                    = "head"
BODY                    = "body"

//HEADER TITLE
TITLE                   = "title"

//HEADERS
H1                      = "H1"
H2                      = "H2"
H3                      = "H3"
H4                      = "H4"
H5                      = "H5"
H6                      = "H6"


//TABLE TAGS
TABLE                   = "table"
TH                      = "th"
TR                      = "tr"
TD                      = "td"

//BLOCKS
DIV                     = "div"
P                       = "p"

//DIVISIONS
BR                      = "br"
HR                      = "hr"

//ATTRIBUTES
COLOR                   = "color"
TEXTCOLOR               = "textcolor"
ALIGN                   = "align"
FONT                    = "font"

//VALUES
COLOR_VALUE             = "ROJO"  | "AMARILLO" | "AZUL" | "MORADO" | "VERDE" | "GRIS" | "ANARANJADO"
ALIGN_VALUE             = "IZQUIERDA" | "DERECHA" | "CENTRADO" 
FONT_VALUE              = [A-Z|a-z|\-|\ ]+
       
//SYMBOLS
CLOSING_LESS_THAN       = "</"
LESS_THAN               = "<"
GREATER_THAN            = ">"
EQUAL                   = "="
SLASH_GREATER_THAN      = "/>"
CR                      = "$$"

%state READ_CREPORT
%state READ_CR_STRING
%state READ_CR_CHAR

%state READ_PLAIN_TEXT
%state READ_HTML_TAG
%state READ_HTML_TAG_ATTRIBUTE
%state READ_HTML_TAG_EQUAL

%state READ_HTML_CLOSING_BRACE
%state READ_HTML_CLOSING_TAG

%state READ_HTML_VALUE_OPENING_QM
%state READ_HTML_VALUE
%state READ_HTML_VALUE_CLOSING_QM

%%


<YYINITIAL> {CR} {
    crcode.setLength(0);
    yybegin(READ_CREPORT);
}

<YYINITIAL> {CLOSING_LESS_THAN} { yybegin(READ_HTML_CLOSING_TAG); return symbol(sym.CLOSING_LESS_THAN, yytext()); }
<YYINITIAL> {LESS_THAN} { yybegin(READ_HTML_TAG); return symbol(sym.LESS_THAN, yytext()); }

<YYINITIAL> {EXPLINE} { ; }
<YYINITIAL> {EXPSPACE} { ; }

<READ_CREPORT>{
    {CR} {
        yybegin(YYINITIAL);
        htmlCreator.addText(compileCP(crcode.toString()));     
    }

    \" {
        crcode.append("\"");
        yybegin(READ_CR_STRING);
    }

    ["'"] {
        crcode.append("'");
        yybegin(READ_CR_CHAR);
    }
    
    [^] {
        crcode.append(yytext());
    }
}

<READ_CR_CHAR> {
    "'" { 
        crcode.append("'");
        yybegin(READ_CREPORT); 
    }
    [^\n\r\"\\]+ { crcode.append( yytext() ); }
    \\t { crcode.append('\t'); }
    \\n { crcode.append('\n'); }
    \\r { crcode.append('\r'); }
    \\\" { crcode.append('\"'); }
    \\ { crcode.append('\\'); }
}

<READ_CR_STRING> {
    \" { 
        crcode.append("\"");
        yybegin(READ_CREPORT); 
    }
    [^\n\r\"\\]+ { crcode.append( yytext() ); }
    \\t { crcode.append('\t'); }
    \\n { crcode.append('\n'); }
    \\r { crcode.append('\r'); }
    \\\" { crcode.append('\"'); }
    \\ { crcode.append('\\'); }
}

<READ_PLAIN_TEXT> {

    {CR} {
        crcode.setLength(0);
        yybegin(READ_CREPORT);
    }

    {CLOSING_LESS_THAN} {
        yybegin(READ_HTML_CLOSING_TAG);
        htmlCreator.addText(string.toString());
        return symbol(sym.CLOSING_LESS_THAN, yytext());
    }

    { LESS_THAN } {
        yybegin(READ_HTML_TAG);
        htmlCreator.addText(string.toString());
        return symbol(sym.LESS_THAN, yytext());
    }

    [^] {
        string.append(yytext());
    }
}

<READ_HTML_TAG>{

    {HTML}  { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.HTML, yytext()); }
    {HEAD}  { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.HEAD, yytext()); }
    {BODY}  { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.BODY, yytext()); }

    {H1}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.H1, yytext()); }
    {H2}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.H2, yytext()); }
    {H3}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.H3, yytext()); }
    {H4}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.H4, yytext()); }
    {H5}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.H5, yytext()); }
    {H6}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext());  return symbol(sym.H6, yytext()); }

    {TITLE} { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.TITLE, yytext()); }
    {TABLE} { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.TABLE, yytext()); }
    {TH}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.TH, yytext()); }    
    {TD}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.TD, yytext()); }
    {TR}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.TR, yytext()); }

    {DIV}   { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.DIV, yytext()); }
    {P}     { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addTag(yytext()); return symbol(sym.P, yytext()); }

    {BR}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addBr(); return symbol(sym.BR, yytext()); }
    {HR}    { yybegin(READ_HTML_TAG_ATTRIBUTE); htmlCreator.addHr(); return symbol(sym.HR, yytext()); }

    {EXPLINE} { ; }

    [^] { 
        String err = "Caracter inesperado [ %s ]";
        Reporter.addLexicError(String.format(err), yyline + 1, yycolumn);
    }
}

<READ_HTML_CLOSING_TAG> {
    
    {HTML}  { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.HTML, yytext());  }
    {HEAD}  { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.HEAD, yytext()); }
    {BODY}  { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.BODY, yytext()); }

    {H1}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.H1, yytext()); }
    {H2}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.H2, yytext()); }
    {H3}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.H3, yytext()); }
    {H4}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.H4, yytext()); }
    {H5}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.H5, yytext()); }
    {H6}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.H6, yytext()); }

    {TITLE} { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.TITLE, yytext()); }
    {TABLE} { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.TABLE, yytext()); }
    {TH}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.TH, yytext()); }    
    {TD}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.TD, yytext()); }
    {TR}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.TR, yytext()); }

    {DIV}   { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.DIV, yytext()); }
    {P}     { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.closeTag(); return symbol(sym.P, yytext()); }

    {BR}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.addBr(); return symbol(sym.BR, yytext()); }
    {HR}    { yybegin(READ_HTML_CLOSING_BRACE); htmlCreator.addHr(); return symbol(sym.HR, yytext()); }

    {EXPLINE} { ; }

    [^] { 
        String err = "Caracter inesperado [ %s ]";
        Reporter.addLexicError(String.format(err), yyline + 1, yycolumn);
    }
}

<READ_HTML_TAG_ATTRIBUTE> {
    {COLOR} { 
        yybegin(READ_HTML_TAG_EQUAL);
        currentPropertyName = "color";
        return symbol(sym.COLOR, yytext()); 
    }
    {TEXTCOLOR} { 
        yybegin(READ_HTML_TAG_EQUAL);
        currentPropertyName = "textcolor";
        return symbol(sym.TEXTCOLOR, yytext()); 
    }
    {ALIGN} { 
        yybegin(READ_HTML_TAG_EQUAL);
        currentPropertyName = "align";
        return symbol(sym.ALIGN, yytext()); 
    }
    {FONT}  { 
        yybegin(READ_HTML_TAG_EQUAL);
        currentPropertyName = "font";
        return symbol(sym.FONT, yytext()); 
    }

    {SLASH_GREATER_THAN} {
        yybegin(YYINITIAL);
        return symbol(sym.SLASH_GREATER_THAN, yytext());         
    }

    {GREATER_THAN} { 
        yybegin(YYINITIAL);
        return symbol(sym.GREATER_THAN, yytext()); 
    }

    {EXPLINE} { ; }

    [^] { 
        String err = "Caracter inesperado [ %s ]";
        Reporter.addLexicError(String.format(err), yyline + 1, yycolumn);
    }
}

<READ_HTML_TAG_EQUAL> {
    {EQUAL} { 
        yybegin(READ_HTML_VALUE_OPENING_QM); 
        return symbol(sym.EQUAL, yytext()); 
    }

    {EXPLINE} { ; }

    [^] { 
        String err = "Caracter inesperado [ %s ]";
        Reporter.addLexicError(String.format(err), yyline + 1, yycolumn);
    }
}


<READ_HTML_CLOSING_BRACE> {

    {GREATER_THAN} { yybegin(YYINITIAL); return symbol(sym.GREATER_THAN, yytext()); }  

    {EXPLINE} { ; }

    [^] { 
        String err = "Caracter inesperado [ %s ]. Linea: %s. Columna: %s.";
        System.err.println(String.format(err, yytext(), yyline, yycolumn)); 
    }
}

<READ_HTML_VALUE_OPENING_QM> {
    [\"] {
        yybegin(READ_HTML_VALUE);
    }

    {EXPLINE} { ; }

    [^] { 
        String err = "Caracter inesperado [ %s ]";
        Reporter.addLexicError(String.format(err), yyline + 1, yycolumn);
    }
}

<READ_HTML_VALUE> {

    {COLOR_VALUE} {
        yybegin(READ_HTML_VALUE_CLOSING_QM);

        if (! "".equals(currentPropertyName)){
            addProperty(currentPropertyName, yytext());    
            currentPropertyName = "";
        }

        return symbol(sym.COLOR_VALUE, yytext());
    }

    {ALIGN_VALUE} {
        yybegin(READ_HTML_VALUE_CLOSING_QM);

        if (! "".equals(currentPropertyName)){
            addProperty(currentPropertyName, yytext());    
            currentPropertyName = "";
        }

        return symbol(sym.ALIGN_VALUE, yytext());
    }

    {FONT_VALUE} {
        yybegin(READ_HTML_VALUE_CLOSING_QM);

        if (! "".equals(currentPropertyName)){
            addProperty(currentPropertyName, yytext());    
            currentPropertyName = "";
        }

        return symbol(sym.FONT_VALUE, yytext());        
    }

    {EXPLINE} { ; }

    [^] { 
        String err = "Caracter inesperado [ %s ]";
        Reporter.addLexicError(String.format(err), yyline + 1, yycolumn);
    }
}

<READ_HTML_VALUE_CLOSING_QM> {
    [\"] {
        yybegin(READ_HTML_TAG_ATTRIBUTE);
    }

    {EXPLINE} { ; }

    [^] { 
        String err = "Caracter inesperado [ %s ]";
        Reporter.addLexicError(String.format(err), yyline + 1, yycolumn);
    }
}

<YYINITIAL> [^] { 
    string.setLength(0);
    string.append(yytext());
    yybegin(READ_PLAIN_TEXT); 
}
