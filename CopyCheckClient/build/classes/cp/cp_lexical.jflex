package cp;
import java_cup.runtime.Symbol;
import json.JsonResult;
import Reporter.Reporter;

%%

%{
    StringBuffer string = new StringBuffer();
    JsonResult result = new JsonResult();
    
    int curr_line = 0;
    int curr_col = 0;

    public void setCurrLine(int curr_line){
        this.curr_line = curr_line;
    }

    public void setCurrCol(int curr_col){
        this.curr_col = curr_col;
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn, yytext());
    }

    private Symbol symbol(int type, Object value, boolean goBack) {
        return new Symbol(type, curr_line + yyline, curr_col + yycolumn + (goBack ? 1 : 0), value);
    }

    private Symbol symbol(int type, Object value) {
        return symbol(type, value, false);
    }

    public void setJsonObject(JsonResult result){
        this.result = result;
    }

    private int getNumOfIndex(String indexable){
        int start = indexable.indexOf("[") + 1;
        int end = indexable.indexOf("]");
        
        if (start > -1 && start < end){
            String number = indexable.substring(start, end);
            return Integer.parseInt(number);
        }
        return -1;
    }
    
    private void createError(){
        String err = "Caracter inesperado [ %s ]";
        Reporter.addLexicError(String.format(err, yytext()), yyline + 1, yycolumn);
    }

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

EXPNUMBER               = [0-9]+
EXPDECIMAL              = [0-9]+("."[0-9]+)
EXPID                   = [A-Za-zñÑ_][_0-9A-Za-z]*
EXPLINE                 = [\ \n]
EXPSPACE                = [\r|\n|\r\n] | [ \t\f]
MULT_COMMENT            = "</" ~ "/>" 
SING_COMMENT            = "->" [^\r\n]*[\r|\n|\r\n]?

//TIPO
ENTERO                  = "entero"
CARACTER                = "caracter"
DECIMAL                 = "decimal"
TEXTO                   = "texto"
BOOLEANO                = "booleano"

//RESULTS 
//SCORE
RESULTSCORE             = "RESULT.score"

//VARIABLES
RESULTVARNAME           = "RESULT.variables[" [0-9]+ "].nombre"
RESULTVARTYPE           = "RESULT.variables[" [0-9]+ "].tipo"
RESULTVARFUNC           = "RESULT.variables[" [0-9]+ "].funcion"
RESULTVARCLAS           = "RESULT.variables[" [0-9]+ "].clase"

RESULTVARS              = "RESULT.variables"
RESULTVARNAMES          = "RESULT.variables.nombre"
RESULTVARSIZE           = "RESULT.variables.cantidad" 

//METODOS
RESULTMETNAME           = "RESULT.metodos[" [0-9]+ "].nombre"
RESULTMETTYPE           = "RESULT.metodos[" [0-9]+ "].tipo"
RESULTMETPARA           = "RESULT.metodos[" [0-9]+ "].parametros"
RESULTMETS              = "RESULT.metodos"
RESULTMETNAMES          = "RESULT.metodos.nombre"
RESULTMETSIZE           = "RESULT.metodos.cantidad" 

//CLASSES
RESULTCLANAME           = "RESULT.clases[" [0-9]+ "].nombre"
RESULTCLAS              = "RESULT.clases"
RESULTCLANAMES          = "RESULT.clases.nombre"
RESULTCLASIZE           = "RESULT.clases.cantidad" 

//COMENTARIOS
RESULTCMNTTXT           = "RESULT.comentarios[" [0-9]+ "].texto"
RESULTCMTS              = "RESULT.comentarios"
RESULTCMTSIZE           = "RESULT.comentarios.cantidad" 

PRINT                   = "PRINT"

BTRUE                   = "true"
BFALSE                  = "false"

//SYMBOLS
OPEN_PARENTHESIS        = "("
CLOSING_PARENTHESIS     = ")"
COMMA                   = ","
SEMICOLON               = ";"
EQUAL                   = "="

//OPERATORS
GREATER_THAN            = ">"
GREATEQUAL_THAN         = ">="
LESS_THAN               = "<"
LESSEQUAL_THAN          = "<="
NOT_EQUAL               = "!="
BEQUAL                  = "=="
PLUSPLUS                = "++"
MINUSMINUS              = "--"


ANDBOP                  = "&&"
ORBOP                   = "||"
NOTBOP                  = "!"

//ARITMETHICS
AR_SUM                  = "+"
AR_SUB                  = "-"
AR_MUL                  = "*"
AR_DIV                  = "/"
AR_MOD                  = "%"

%state READ_STRING
%state READ_CHAR
%state FINISH_READING_CHAR

%%

<YYINITIAL> {SING_COMMENT} { 
    System.out.println(yytext());
}

<YYINITIAL> {MULT_COMMENT} {
    System.out.println(yytext());
}  

// GET RESULT SCORE
<YYINITIAL> {RESULTSCORE} { 
    double score = result.getScore();
    return symbol(sym.EXPDECIMAL, String.valueOf(score));
}

// VARNAME
//GET VARNAME AT INDEX
<YYINITIAL> {RESULTVARNAME} {
    try {
        int index = getNumOfIndex(yytext()); 
        String value = result.getVarName(index);
        return symbol(sym.STRING_LITERAL, value);
    } catch(Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }
}

//GET VARTYPE AT INDEX
<YYINITIAL> {RESULTVARTYPE} {
    try {
        int index = getNumOfIndex(yytext()); 
        String value = result.getVarType(index);
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }
}

//GET VARFUNC AT INDEX
<YYINITIAL> {RESULTVARFUNC} {
    try {
        int index = getNumOfIndex(yytext()); 
        String value = result.getVarFunc(index);
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }
}

//GET VARCLASS AT INDEX
<YYINITIAL> {RESULTVARCLAS} {
    try {
        int index = getNumOfIndex(yytext()); 
        String value = result.getVarClass(index);
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }
}

//GET VARNAMES
<YYINITIAL> {RESULTVARNAMES} {
    try {
        String value = result.getVarNames();
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }
}

//GET VARS SIZE
<YYINITIAL> {RESULTVARSIZE} {
    int value = result.getVarSize();
    return symbol(sym.EXPNUMBER, String.valueOf(value));
}

//GET ALL VARS
<YYINITIAL> {RESULTVARS} {
    try {
        String value = result.getVars();
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }
}

//METODOS

// RESULT METHOD NAME
<YYINITIAL> {RESULTMETNAME} {
    try {
        int index = getNumOfIndex(yytext());
        String value = result.getMethodName(index);
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    } 
}

//RESUTL METHOD TYPE
<YYINITIAL> {RESULTMETTYPE} {
    try {
        int index = getNumOfIndex(yytext());
        String value = result.getMethodType(index);
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }
}

//RESULT METHOD PARAMS
<YYINITIAL> {RESULTMETPARA} {
    try {
        int index = getNumOfIndex(yytext());
        int value = result.getMethodParams(index);
        return symbol(sym.EXPNUMBER, String.valueOf(value));
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.EXPNUMBER, "-1");
    } 
}

//RESULT METHOD NAMES
<YYINITIAL> {RESULTMETNAMES} {
    String value = result.getMethodNames();
    return symbol(sym.STRING_LITERAL, value);
}

//RESULT METHODS SIZE
<YYINITIAL> {RESULTMETSIZE} {
    int value = result.getMethodSize();
    return symbol(sym.EXPNUMBER, String.valueOf(value));
}

//RESULT METHODS
<YYINITIAL> {RESULTMETS} {
    String value = result.getMethods();
    return symbol(sym.STRING_LITERAL, value);
}

//CLASSES

<YYINITIAL> {RESULTCLANAME} {
    try {
        int index = getNumOfIndex(yytext());
        String value = result.getClassName(index);
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e) {
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }
}

<YYINITIAL> {RESULTCLANAMES} {
    String value = result.getClassNames();
    return symbol(sym.STRING_LITERAL, value);
}

<YYINITIAL> {RESULTCLASIZE} {
    int value = result.getClassesSize();
    return symbol(sym.EXPNUMBER, String.valueOf(value));
}

<YYINITIAL> {RESULTCLAS} {
    String value = result.getClasses();
    return symbol(sym.STRING_LITERAL, value);
}

//COMENTS

<YYINITIAL> {RESULTCMNTTXT} {
    try {
        int index = getNumOfIndex(yytext());
        String value = result.getComent(index);
        return symbol(sym.STRING_LITERAL, value);
    } catch (Exception e){
        Reporter.log(e.getMessage());
        return symbol(sym.STRING_LITERAL, "");
    }    
}

<YYINITIAL> {RESULTCMTS} {
    String value = result.getComents();
    return symbol(sym.STRING_LITERAL, value);
}

<YYINITIAL> {RESULTCMTSIZE} {
    int value = result.getComentsSize();
    return symbol(sym.EXPNUMBER, String.valueOf(value));
}

<YYINITIAL> {PRINT} {
    return symbol(sym.PRINT, yytext());
}

<YYINITIAL> {EXPDECIMAL} { return symbol(sym.EXPDECIMAL); }
<YYINITIAL> {EXPNUMBER} { return symbol(sym.EXPNUMBER); }

<YYINITIAL> {ENTERO} {return symbol(sym.ENTERO, yytext());}
<YYINITIAL> {CARACTER} {return symbol(sym.CARACTER, yytext());}
<YYINITIAL> {DECIMAL} {return symbol(sym.DECIMAL, yytext());}
<YYINITIAL> {TEXTO}  {return symbol(sym.TEXTO, yytext());}
<YYINITIAL> {BOOLEANO} {return symbol(sym.BOOLEANO, yytext());}

<YYINITIAL> {OPEN_PARENTHESIS} { return symbol(sym.OPEN_PARENTHESIS); }
<YYINITIAL> {CLOSING_PARENTHESIS} { return symbol(sym.CLOSING_PARENTHESIS); }
<YYINITIAL> {COMMA} { return symbol(sym.COMMA); }
<YYINITIAL> {SEMICOLON} { return symbol(sym.SEMICOLON); }
<YYINITIAL> {EQUAL} { return symbol(sym.EQUAL); }

<YYINITIAL> {PLUSPLUS} { return symbol(sym.PLUSPLUS); }
<YYINITIAL> {MINUSMINUS} { return symbol(sym.MINUSMINUS); }

<YYINITIAL> {AR_SUM} { return symbol(sym.AR_SUM); }
<YYINITIAL> {AR_SUB} { return symbol(sym.AR_SUB); }
<YYINITIAL> {AR_MUL} { return symbol(sym.AR_MUL); }
<YYINITIAL> {AR_DIV} { return symbol(sym.AR_DIV); }
<YYINITIAL> {AR_MOD} { return symbol(sym.AR_MOD); }

<YYINITIAL> {GREATER_THAN} { return symbol(sym.GREATER_THAN); }
<YYINITIAL> {GREATEQUAL_THAN} { return symbol(sym.GREATEQUAL_THAN); }
<YYINITIAL> {LESS_THAN} { return symbol(sym.LESS_THAN); }
<YYINITIAL> {LESSEQUAL_THAN} { return symbol(sym.LESSEQUAL_THAN); }
<YYINITIAL> {NOT_EQUAL} { return symbol(sym.NOT_EQUAL); }
<YYINITIAL> {BEQUAL} { return symbol(sym.BEQUAL); }

<YYINITIAL> {ANDBOP} { return symbol(sym.ANDBOP); }
<YYINITIAL> {ORBOP} { return symbol(sym.ORBOP); }
<YYINITIAL> {NOTBOP} { return symbol(sym.NOTBOP); }  

<YYINITIAL> {BTRUE} {
    return symbol(sym.BTRUE, yytext());
}

<YYINITIAL> {BFALSE} {
    return symbol(sym.BFALSE, yytext());
}

<YYINITIAL> {EXPID} { return symbol(sym.EXPID); }

<YYINITIAL> \" { 
    string.setLength(0); 
    yybegin(READ_STRING); 
}

<READ_STRING> {
    \" { 
        yybegin(YYINITIAL); 
        return symbol(sym.STRING_LITERAL, string.toString()); 
    }

    [^\n\r\"\\]+ { string.append( yytext() ); }
    \\t { string.append('\t'); }
    \\n { string.append('\n'); }
    \\r { string.append('\r'); }
    \\\" { string.append('\"'); }
    \\ { string.append('\\'); }
}

<YYINITIAL> \' {
    string.setLength(0);
    yybegin(READ_CHAR);
}

<READ_CHAR> {
    [^] {
        yybegin(FINISH_READING_CHAR);
        string.append(yytext());
    }
}

<FINISH_READING_CHAR>{
    ' {
        yybegin(YYINITIAL);
        return symbol(sym.CHAR_LITERAL, string.toString());
    }
    [^] {
        createError();
    }
}

<YYINITIAL> {EXPSPACE} { ; }
<YYINITIAL> {EXPLINE} { ; }

[^] { 
    String err = "Caracter inesperado [ %s ]";
    Reporter.addLexicError(String.format(err), yyline + 1, yycolumn);
}