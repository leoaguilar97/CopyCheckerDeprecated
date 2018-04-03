package cp;

import JFlex.SilentExit;
import cp.SymTable.SymTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import Reporter.Reporter;
import json.JsonResult;

/**
 * 
 * @author leoag
 */
public class Compiler {
    
    private SymTable symTable;
    private JsonResult jsonResult;
    
        private int currLine;

    /**
     * Get the value of currLine
     *
     * @return the value of currLine
     */
    public int getCurrLine() {
        return currLine;
    }

    /**
     * Set the value of currLine
     *
     * @param currLine new value of currLine
     */
    public void setCurrLine(int currLine) {
        this.currLine = currLine;
    }

    private int currCol;

    /**
     * Get the value of currCol
     *
     * @return the value of currCol
     */
    public int getCurrCol() {
        return currCol;
    }

    /**
     * Set the value of currCol
     *
     * @param currCol new value of currCol
     */
    public void setCurrCol(int currCol) {
        this.currCol = currCol;
    }


    /**
     * Get the value of symTable
     *
     * @return the value of symTable
     */
    public SymTable getSymTable() {
        return symTable;
    }

    /**
     * Obtiene el objeto Json de resultado
     * @return
     */
    public JsonResult getJsonResult() {
        return jsonResult;
    }

    /**
     * Asigna el objeto JSON actual
     * @param jsonResult
     */
    public void setJsonResult(JsonResult jsonResult) {
        this.jsonResult = jsonResult;
    }

    /**
     * Set the value of symTable
     *
     * @param symTable new value of symTable
     */
    public void setSymTable(SymTable symTable) {
        this.symTable = symTable;
    }

    /** 
     * Crea un compilador de CReport
     * @param symTable
     * @param jsonResult
     * @param currLine
     * @param currCol
     */
    public Compiler(SymTable symTable, JsonResult jsonResult, int currLine, int currCol) {
        this.symTable = symTable;
        this.jsonResult = jsonResult;
        this.currCol = currCol;
        this.currLine = currLine;
    }    
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        createCompilerFiles();
    }
    
    private static void createCompilerFiles(){
        try {
            System.out.println("Generando compilador CP...");
            final String SRC_ROUTE = "src/cp/";
            String opcFlex[] = { SRC_ROUTE + "cp_lexical.jflex", "-d", SRC_ROUTE };
            JFlex.Main.generate(opcFlex);
            String opcCUP[] = { "-destdir", SRC_ROUTE, "-parser", "parser", SRC_ROUTE + "cp_grammar.cup" };
            java_cup.Main.main(opcCUP);
        } catch (SilentExit e) {
            System.out.println("Error al compilar archivos: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
        }       
    } 
    
    /**
     * Compila codigo CReport
     * @param code codigo CReport
     * @return Todo string generado con el print
     */
    public String Analyze(String code){
        try {            
            scanner scan = new scanner(new BufferedReader( new StringReader(code)));     
            
            scan.setCurrCol(currCol);
            
            scan.setCurrLine(currLine);
            
            scan.setJsonObject(jsonResult);
            
            parser cp_parser = new parser(scan);
            cp_parser.setSymTable(symTable);  
            
            cp_parser.parse();           
            
            /*
            
            Symbol c = scan.next_token();
            
            while (c != null && c.value != null){
                System.out.println("CR Token: " + c.value);
                c = scan.next_token();
            }
            
            */
            
            return cp_parser.getPrintResult();
            
        } catch (IOException ex) {
            Reporter.log("Error al compilar codigo CP: " + ex.getMessage());
        } catch (Exception ex) {
            Reporter.log("Error al abrir o compilar codigo CP: " + ex.getMessage());
        }
        
        return "";
    }    
}
