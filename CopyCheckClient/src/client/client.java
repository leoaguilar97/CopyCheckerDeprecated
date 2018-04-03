package client;

import ui.ClientMainWindow;
import Reporter.Reporter;

/**
 * Cliente principal
 * @author Leonel Aguilar
 */
public class client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClientMainWindow mw = new ClientMainWindow();
        mw.setVisible(true);
        Reporter.setConsole(mw.getConsole());
    }    
}
