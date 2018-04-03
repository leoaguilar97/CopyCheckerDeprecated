package Reporter;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 * Reportador de errores
 * @author Leonel Aguilar
 */
public class Reporter {
    
    private static ArrayList<Error> semantics;
    private static ArrayList<Error> syntax;
    private static ArrayList<Error> lexics;
    
    private static JTextArea console;
    
    /**
     * Reinicia los errores
     */
    public static void reset(){
        semantics = new ArrayList<>();
        syntax = new ArrayList<>();
        lexics = new ArrayList<>();
    }

    /**
     * Guarda los errores
     * @param error
     */
    public static void log(Error error){
        
        String errMessage;
        
        //Log as message
        if("APLICACION".equals(error.getType())){
            errMessage = error.getMessage();
            System.out.println("[INFO] Reporter: " + errMessage);
        } 
        //Log as error
        else {            
            errMessage = error.toString();
            System.err.println("[ERROR] Reporter: " + error.toString());        
        }

        if (console != null){
            console.append(errMessage + "\n");
        }
    }
    
    /**
     * Guarda un error de aplicacion sin agregarlo a ninguna lista
     * @param error error a guardar
     */
    public static void log(String error){
        log(error, true);
    }
    
    /**
     * Anade a la consola un error o un mensaje
     * @param error error a mostrar
     * @param isError si es error o solo log
     */
    public static void log(String error, boolean isError){
        if (isError){
            log(new Error(-1, -1, -1, error, "APLICACION"));
        } else {
            System.out.println(error);
            if (console != null) console.append(error + "\n");
        }
    }
        
    /**
     * Anade un error lexico
     * @param message
     * @param line
     * @param column
     */
    public static void addLexicError(String message, int line, int column) {
        lexics.add(createError(line, column, message, "Lexico"));
    }    
    
    /**
     * Anade un error sintactico
     * @param message
     * @param line
     * @param column
     */
    public static void addSyntaxError(String message, int line, int column){
        syntax.add(createError(line, column, message, "Sintaxis"));
    }   

    /**
     * Anade un error semantico
     * @param message
     * @param line
     * @param column
     */
    public static void addSemanticError(String message, int line, int column){
        semantics.add(createError(line, column, message, "Semantico"));
    }
    
    public static boolean hasErrors(){
        return !(semantics.isEmpty() && lexics.isEmpty() && syntax.isEmpty());
    }
        
    /**
     * Genera un HTML para representar los errores
     * @return Datos de html
     * @throws Exception cuando no hay errores almacenados
     */
    public static String generateAsHtml() throws Exception {
        
        if (!hasErrors()){
            throw new Exception("No existe ningun error.");            
        }
                                 
        return String.format(getHtmlFormat(), getErrorsAsHtmlRows());
    }

    /**
     * Define la consola en la que hay que imprimir
     * @param console
     */
    public static void setConsole(JTextArea console){
        Reporter.console = console;
    }
    
    private static String getErrorListAsRows(ArrayList<Error> erList){
        StringBuilder result = new StringBuilder();
        
        if (erList.isEmpty()){
            return "";
        } else {
            erList.forEach(er -> { result.append(er.getAsHtmlTableRow()); });            
        }
        
        return result.toString();
    }
    
    private static String getErrorsAsHtmlRows() {
        StringBuilder result = new StringBuilder();
        
        if (!lexics.isEmpty()){
            result.append(getSeparator("Errores Lexicos"));
            result.append(getErrorListAsRows(lexics));
        }
        if (!syntax.isEmpty()){
            result.append(getSeparator("Errores Sintacticos"));
            result.append(getErrorListAsRows(syntax));
        }
        if (!semantics.isEmpty()){
            result.append(getSeparator("Errores Semanticos"));
            result.append(getErrorListAsRows(semantics));
        }
        
        return result.toString();
    }
    
    private static String getHtmlFormat(){
        return "<!DOCTYPE html> <html> <head> <title>Reporte de errores</title> </head> <body> <style type=\"text/css\"> .tg {border-collapse:collapse;border-spacing:0;margin:0px auto;} .tg td{font-family:Arial, sans-serif;font-size:14px;padding:6px 20px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;} .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:6px 20px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;} .tg .tg-7uzy{vertical-align:top} .tg .tg-n385{font-weight:bold;background-color:#ffce93;color:#ffffff;text-align:center;vertical-align:top} .tg .tg-4jb6{background-color:#ffffff;color:#333333;text-align:center;vertical-align:top} .tg .tg-itju{background-color:#fd6864;color:#ffffff;vertical-align:top} .tg .tg-25wf{font-weight:bold;font-family:\"Lucida Console\", Monaco, monospace !important;;background-color:#ffccc9;color:#cb0000;text-align:center;vertical-align:top} .tg .tg-yw4l{vertical-align:top} .tg .tg-z9dd{background-color:#ffffff;color:#333333;text-align:center;vertical-align:top} th.tg-sort-header::-moz-selection { background:transparent; }th.tg-sort-header::selection { background:transparent; }th.tg-sort-header { cursor:pointer; }table th.tg-sort-header:after { content:''; float:right; margin-top:7px; border-width:0 4px 4px; border-style:solid; border-color:#404040 transparent; visibility:hidden; }table th.tg-sort-header:hover:after { visibility:visible; }table th.tg-sort-desc:after,table th.tg-sort-asc:after,table th.tg-sort-asc:hover:after { visibility:visible; opacity:0.4; }table th.tg-sort-desc:after { border-bottom:none; border-width:4px 4px 0; }@media screen and (max-width: 767px) {.tg {width: auto !important;}.tg col {width: auto !important;}.tg-wrap {overflow-x: auto;-webkit-overflow-scrolling: touch;margin: auto 0px;}}</style> <div class=\"tg-wrap\"><table id=\"tg-ATCzm\" class=\"tg\"> <tr> <th class=\"tg-itju\">#</th> <th class=\"tg-25wf\">TIPO</th> <th class=\"tg-25wf\">LINEA</th> <th class=\"tg-25wf\">COLUMNA</th> <th class=\"tg-25wf\">MENSAJE</th> </tr> %s </table></div> <script type=\"text/javascript\" charset=\"utf-8\">var TgTableSort=window.TgTableSort||function(n,t){\"use strict\";function r(n,t){for(var e=[],o=n.childNodes,i=0;i<o.length;++i){var u=o[i];if(\".\"==t.substring(0,1)){var a=t.substring(1);f(u,a)&&e.push(u)}else u.nodeName.toLowerCase()==t&&e.push(u);var c=r(u,t);e=e.concat(c)}return e}function e(n,t){var e=[],o=r(n,\"tr\");return o.forEach(function(n){var o=r(n,\"td\");t>=0&&t<o.length&&e.push(o[t])}),e}function o(n){return n.textContent||n.innerText||\"\"}function i(n){return n.innerHTML||\"\"}function u(n,t){var r=e(n,t);return r.map(o)}function a(n,t){var r=e(n,t);return r.map(i)}function c(n){var t=n.className||\"\";return t.match(/\\S+/g)||[]}function f(n,t){return-1!=c(n).indexOf(t)}function s(n,t){f(n,t)||(n.className+=\" \"+t)}function d(n,t){if(f(n,t)){var r=c(n),e=r.indexOf(t);r.splice(e,1),n.className=r.join(\" \")}}function v(n){d(n,L),d(n,E)}function l(n,t,e){r(n,\".\"+E).map(v),r(n,\".\"+L).map(v),e==T?s(t,E):s(t,L)}function g(n){return function(t,r){var e=n*t.str.localeCompare(r.str);return 0==e&&(e=t.index-r.index),e}}function h(n){return function(t,r){var e=+t.str,o=+r.str;return e==o?t.index-r.index:n*(e-o)}}function m(n,t,r){var e=u(n,t),o=e.map(function(n,t){return{str:n,index:t}}),i=e&&-1==e.map(isNaN).indexOf(!0),a=i?h(r):g(r);return o.sort(a),o.map(function(n){return n.index})}function p(n,t,r,o){for(var i=f(o,E)?N:T,u=m(n,r,i),c=0;t>c;++c){var s=e(n,c),d=a(n,c);s.forEach(function(n,t){n.innerHTML=d[u[t]]})}l(n,o,i)}function x(n,t){var r=t.length;t.forEach(function(t,e){t.addEventListener(\"click\",function(){p(n,r,e,t)}),s(t,\"tg-sort-header\")})}var T=1,N=-1,E=\"tg-sort-asc\",L=\"tg-sort-desc\";return function(t){var e=n.getElementById(t),o=r(e,\"tr\"),i=o.length>0?r(o[0],\"td\"):[];0==i.length&&(i=r(o[0],\"th\"));for(var u=1;u<o.length;++u){var a=r(o[u],\"td\");if(a.length!=i.length)return}x(e,i)}}(document);document.addEventListener(\"DOMContentLoaded\",function(n){TgTableSort(\"tg-ATCzm\")});</script> </body> </html>";
    }
       
    private static int getNumber(){
        return semantics.size() + syntax.size() + lexics.size() + 1;
    }
    
    private static String getSeparator(String separator){
        String format = "<tr> <td class=\"tg-n385\" colspan=\"5\">%s</td> </tr>";
        return String.format(format, separator);
    }
    
    private static Error createError(int line, int col, String message, String type){
        Error e = new Error(getNumber(), line, col, type, message);
        log(e);
        return e;
    }
    
}
