package javacompiler;
import java_cup.runtime.Symbol;
import proyectdata.Proyect; 
import ui.Reporter;

%%
%{
    private StringBuffer string = new StringBuffer();
    private Proyect proyect = new Proyect();
    
    public void setProyect(Proyect p){
        proyect = p;
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn, yytext());
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

%cup
%class scanner
%public
%line
%char
%column
%full


 //EXPRESIONES REGULARES

EXPNUMBER               = [0-9]+("." [0-9]+)?
EXPID                   = [A-Za-zñÑ][_0-9A-Za-z]*
EXPLINE                 = [\ \n]
EXPSPACE                = [\r|\n|\r\n] | [ \t\f]
MULT_COMMENT            = "/*" ~ "*/" 
SING_COMMENT            = "//" [^\r\n]*[\r|\n|\r\n]?

//SYMBOLS
OPEN_PARENTHESIS        = "("
CLOSING_PARENTHESIS     = ")"
OPEN_BRACES             = "{"
CLOSING_BRACES          = "}"
COMMA                   = ","
SEMICOLON               = ";"
POINT                   = "."
DOUBLEPOINT             = ":"
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

//ARITMETHICS
AR_SUM                  = "+"
AR_SUB                  = "-"
AR_MUL                  = "*"
AR_DIV                  = "/"
AR_MOD                  = "%"

//CLASSES AND IMPORTS
IMPRT                   = "import"
PCKGE                   = "package"

//DATA TYPE
INTGR                   = "int"
CHARA                   = "char"
BOOLE                   = "boolean"
DOUBL                   = "double"
ILONG                   = "long"
STRNG                   = "String"

BTRUE                   = "true"
BFALSE                  = "false"

//VISIBILITY

PUBVIS                  = "public"
PRIVIS                  = "private"
PROVIS                  = "protected"
STATVIS                 = "static"

//MODIFIER

CLASSDEC                = "class"
NEWDEC                  = "new"
RETRN                   = "return"
FINALMOD                = "final"
VOIDTYPE                = "void"

//CONDITIONALS

IFCOND                  = "if"
ELSECOND                = "else"
SWITCHCOND              = "switch"
CASECOND                = "case"
BREAKCOND               = "break"
CONTINUECOND            = "continue"

//LOOPS

FORLOOP                 = "for"
WHILELOOP               = "while"
DOLOOP                  = "do"

//BOOLEAN OPERATORS

ANDBOP                  = "&&"
ORBOP                   = "||"
NOTBOP                  = "!"

%state READ_STRING
%state READ_CHAR
%state FINISH_READING_CHAR

%%
//READ COMMENTS

<YYINITIAL> {SING_COMMENT} { 
    proyect.addComent(yytext());
}

<YYINITIAL> {MULT_COMMENT} {
    proyect.addComent(yytext());
}  

<YYINITIAL> {OPEN_PARENTHESIS} { return symbol(sym.OPEN_PARENTHESIS); }
<YYINITIAL> {OPEN_BRACES} { return symbol(sym.OPEN_BRACES); }
<YYINITIAL> {CLOSING_PARENTHESIS} { return symbol(sym.CLOSING_PARENTHESIS); }
<YYINITIAL> {CLOSING_BRACES} { return symbol(sym.CLOSING_BRACES); }
<YYINITIAL> {COMMA} { return symbol(sym.COMMA); }
<YYINITIAL> {SEMICOLON} { return symbol(sym.SEMICOLON); }
<YYINITIAL> {EQUAL} { return symbol(sym.EQUAL); }
<YYINITIAL> {POINT} { return symbol(sym.POINT); }
<YYINITIAL> {DOUBLEPOINT} { return symbol(sym.DOUBLEPOINT); }

<YYINITIAL> {PCKGE} { return symbol(sym.PCKGE); }
<YYINITIAL> {IMPRT} { return symbol(sym.IMPRT); }

<YYINITIAL> {INTGR} { return symbol(sym.INTGR); }
<YYINITIAL> {BOOLE} { return symbol(sym.BOOLE); }
<YYINITIAL> {CHARA} { return symbol(sym.CHARA); }
<YYINITIAL> {DOUBL} { return symbol(sym.DOUBL); }
<YYINITIAL> {ILONG} { return symbol(sym.ILONG); }
<YYINITIAL> {STRNG} { return symbol(sym.STRNG); }

<YYINITIAL> {BTRUE} { return symbol(sym.BTRUE); }
<YYINITIAL> {BFALSE} { return symbol(sym.BFALSE); }

<YYINITIAL> {PUBVIS} { return symbol(sym.PUBVIS); }
<YYINITIAL> {PRIVIS} { return symbol(sym.PRIVIS); }
<YYINITIAL> {PROVIS} { return symbol(sym.PROVIS); }
<YYINITIAL> {STATVIS} { return symbol(sym.STATVIS); }

<YYINITIAL> {CLASSDEC} { return symbol(sym.CLASSDEC); }
<YYINITIAL> {NEWDEC} { return symbol(sym.NEWDEC); }
<YYINITIAL> {RETRN} { return symbol(sym.RETRN); }
<YYINITIAL> {FINALMOD} { return symbol(sym.FINAL_MOD); }
<YYINITIAL> {VOIDTYPE} { return symbol(sym.VOIDTYPE); }

<YYINITIAL> {IFCOND} { return symbol(sym.IFCOND); }
<YYINITIAL> {ELSECOND} { return symbol(sym.ELSECOND); }
<YYINITIAL> {SWITCHCOND} { return symbol(sym.SWITCHCOND); }
<YYINITIAL> {CASECOND} { return symbol(sym.CASECOND); }

<YYINITIAL> {WHILELOOP} { return symbol(sym.WHILELOOP); }
<YYINITIAL> {FORLOOP} { return symbol(sym.FORLOOP); }
<YYINITIAL> {DOLOOP} { return symbol(sym.DOLOOP); }
<YYINITIAL> {BREAKCOND} { return symbol(sym.BREAKCOND); }   
<YYINITIAL> {CONTINUECOND} { return symbol(sym.CONTINUECOND); } 

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

<YYINITIAL> {EXPNUMBER} { return symbol(sym.EXPNUMBER); }
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
}

<YYINITIAL> {EXPSPACE} { ; }
<YYINITIAL> {EXPLINE} { ; }

[^] { 
    String err = "Caracter inesperado < %s >. Linea: %s. Columna: %s.";
    Reporter.err(String.format(err, yytext(), yyline, yycolumn)); 
}