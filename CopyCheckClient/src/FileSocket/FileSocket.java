package FileSocket;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import Reporter.Reporter;

/**
 * Socket del cliente para comunicacion entre servidor
 * @author Leonel Aguilar
 */
public class FileSocket {
            
    /**
     *
     * @param proy1
     * @param proy2
     * @param map1 Mapa con el nombre y datos del proyecto 1
     * @param map2 Mapa con el nombre y datos del proyecto 2
     * @return Regresa un archivo JSON con los datos de score
     * @throws IOException
     */
    public static String sendFiles(
        String proy1, String proy2
    ) throws IOException {
               
        String proyectSeparator = " ##eop## ";
        
        StringBuilder sb = new StringBuilder();
               
        sb.append(proy1);
        sb.append(proyectSeparator);
        sb.append(proy2);
                                
        //OPEN CONNECTION
        try (Socket socket = new Socket("localhost", 9090)) {
        
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            
            output.writeObject(sb.toString());
            
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        
            String json = (String) input.readObject();
                                    
            return json;
        }
        catch (Exception ex){
            Reporter.log("Error de envio: " + ex.getMessage());
            return "";
        } 
    }
}
