package json;

import java_cup.runtime.Symbol;

%%

%{
    StringBuffer string = new StringBuffer();

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
%ignorecase  

 //EXPRESIONES REGULARES
EXPNUMBER               = [\-]?[0-9]+("." [0-9]+)?
EXPLINE                 = [\ \n]
EXPSPACE                = [\r|\n|\r\n] | [ \t\f]

//SYMBOLS
OPEN_BRACES             = "{"
CLOSING_BRACES          = "}"
COMMA                   = ","
DOUBLEPOINT             = ":"
OPEN_SQR_BRACES         = "["
CLOSING_SQR_BRACES      = "]"

//VALUES
NULL_VALUE              = "null"
TRUE_VALUE              = "true"
FALSE_VALUE             = "false"

%state READ_STRING

%%

<YYINITIAL> {OPEN_BRACES} { return symbol(sym.OPEN_BRACES); }
<YYINITIAL> {CLOSING_BRACES} { return symbol(sym.CLOSING_BRACES); }

<YYINITIAL> {OPEN_SQR_BRACES} { return symbol(sym.OPEN_SQR_BRACES); }
<YYINITIAL> {CLOSING_SQR_BRACES} { return symbol(sym.CLOSING_SQR_BRACES); }

<YYINITIAL> {COMMA} { return symbol(sym.COMMA); }

<YYINITIAL> {DOUBLEPOINT} { return symbol(sym.DOUBLEPOINT); }

<YYINITIAL> {EXPNUMBER} { return symbol(sym.EXPNUMBER); }


<YYINITIAL> {NULL_VALUE} { return symbol(sym.NULL_VALUE); }

<YYINITIAL> {TRUE_VALUE} { return symbol(sym.BOOL); }
<YYINITIAL> {FALSE_VALUE} { return symbol(sym.BOOL); }

<YYINITIAL> {EXPLINE} { ; }
<YYINITIAL> {EXPSPACE} { ; }

<YYINITIAL> \" { 
    string.setLength(0); 
    yybegin(READ_STRING); 
}

<READ_STRING> {
    \" { 
        yybegin(YYINITIAL); 
        return symbol(sym.ID, string.toString()); 
    }
    [^\n\r\"\\]+ { string.append( yytext() ); }
    \\t { string.append('\t'); }
    \\n { string.append('\n'); }
    \\r { string.append('\r'); }
    \\\" { string.append('\"'); }
    \\ { string.append('\\'); }
}

[^] { 
    String err = "Caracter inesperado < %s >. Linea: %s. Columna: %s.";
    System.err.println(String.format(err, yytext(), yyline, yycolumn)); 
}