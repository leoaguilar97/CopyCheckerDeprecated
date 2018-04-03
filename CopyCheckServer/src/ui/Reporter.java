package ui;

import javax.swing.JTextArea;

/**
 *
 * @author Leonel Aguilar
 */
public class Reporter {
    
    private static JTextArea console = null;
    
    public static void log(String message){
        if (console != null){
            console.append(message + "\n");
        }
        System.out.println(message);
    }
    
    public static void err(String error){
        if (console != null){
            console.append(error + "\n");
        }
        System.err.println(error);
    }
    
    public static void setConsole(JTextArea console){
        Reporter.console = console;
    }
}
