package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;

/**
 *
 * @author Leonel Aguilar
 */
public class compiler {
    
    private static final JTree treeView = new JTree(); 
    
    public static void main(String[] args) {
        createCompilerFiles();
    }
    
    private static void createCompilerFiles(){
        try {
            System.out.println("Generando compilaror JSON...");
            final String SRC_ROUTE = "src/json/";
            String opcFlex[] = { SRC_ROUTE + "lexical.jflex", "-d", SRC_ROUTE };
            JFlex.Main.generate(opcFlex);
            String opcCUP[] = { "-destdir", SRC_ROUTE, "-parser", "parser", SRC_ROUTE + "grammar.cup" };
            java_cup.Main.main(opcCUP);
        } catch (Exception e) {
            System.out.println("Error al compilar archivos: " + e.getMessage());
        }       
    }  
    
    public static void Analyze(String code, JsonVisualizer tree){
        try {            
            scanner scan = new scanner(new BufferedReader( new StringReader(code)));
            parser json_parser = new parser(scan);
            json_parser.setJsonVisualizer(tree);
            json_parser.parse();
        } catch (IOException ex) {
            Logger.getLogger(compiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
