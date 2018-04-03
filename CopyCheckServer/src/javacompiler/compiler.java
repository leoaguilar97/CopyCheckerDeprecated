package javacompiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import proyectdata.Proyect;

/**
 *
 * @author leoag
 */
public class compiler {
    
    public static void main(String[] args){
        createCompilerFiles();        
    }
    
    private static void createCompilerFiles(){
        try {
            System.out.println("Generando compilador Java...");
            final String SRC_ROUTE = "src/javacompiler/";
            String opcFlex[] = { SRC_ROUTE + "lexical.jflex", "-d", SRC_ROUTE };
            JFlex.Main.generate(opcFlex);
            String opcCUP[] = { "-destdir", SRC_ROUTE, "-parser", "parser", SRC_ROUTE + "grammar.cup" };
            java_cup.Main.main(opcCUP);
        } catch (Exception e) {
            System.out.println("Error al compilar archivos java: " + e.getMessage());
        }       
    } 

    public static Proyect Analyze(String code) {
        Proyect p = new Proyect();
        return Analyze(code, p);
    }
    
    public static Proyect Analyze(String code, Proyect p){
        try {            
            scanner scan = new scanner(new BufferedReader( new StringReader(code)));
            scan.setProyect(p);
            /*
            Symbol c;
            
            while((c = scan.next_token()).value != null){
                System.out.println("Token actual: " + c.value);
            }*/
            
            parser java_parser = new parser(scan);
            java_parser.setProyect(p);
            java_parser.parse();
            
            p.resetClass(); 
            
            return p;
        } catch (IOException ex) {
            Logger.getLogger(compiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
