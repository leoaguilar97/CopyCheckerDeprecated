package cp.html;

import Reporter.Reporter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import json.JsonResult;

/**
 * Compilador de HTML y CREPORT
 * @author Leonel Aguilar
 */
public class compiler {
    public static void main(String[] args) {
        createCompilerFiles();
    }
    
    private static void createCompilerFiles(){
        try {
            System.out.println("Generando compilaror HTML...");
            final String SRC_ROUTE = "src/cp/html/";
            String opcFlex[] = { SRC_ROUTE + "html_lexical.jflex", "-d", SRC_ROUTE };
            JFlex.Main.generate(opcFlex);
            String opcCUP[] = { "-destdir", SRC_ROUTE, "-parser", "parser", SRC_ROUTE + "html_grammar.cup" };
            java_cup.Main.main(opcCUP);
        } catch (Exception e) {
            System.out.println("Compilador HTML: Error al compilar archivos: " + e.getMessage());
        }       
    }  
    
    /**
     * Analiza y genera el HTML con CREPORT
     * @param code codigo a analizar
     * @param jsonResult
     * @return html generado
     */
    public static String Analyze(String code, JsonResult jsonResult){
        try {

            HtmlCreator html_creator = new HtmlCreator();
            
            scanner scan = new scanner(new BufferedReader( new StringReader(code)));
            
            scan.setJsonResult(jsonResult);
            
            scan.setHtmlCreator(html_creator);
                       
            parser html_parser = new parser(scan);    
            
            html_parser.parse();
                       
            return html_creator.getFinalHtml();
            
        } catch (IOException ex) {
            Reporter.log("Error al compilar codigo HTML: " + ex.getMessage(), true);
        } catch (Exception ex) {
            Reporter.log("Error al abrir o compilar codigo HTML: " + ex.getMessage(), true);
        }
        
        return "";
    }

}
