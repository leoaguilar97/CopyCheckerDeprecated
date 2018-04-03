package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectdata.Proyect;
import proyectdata.analyzer;
import ui.Reporter;
import ui.ServerMainWindow;

/**
 * 
 * @author Leonel Aguilar
 */
public class server {
    
    public static void readFiles(File folder, Proyect p1){
                
        if (!folder.isDirectory() ) {
            Reporter.err("No se detecto ninguna carpeta de archivos.");
            return;
        }
                         
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                readFiles(fileEntry, p1);
            } 
            else if (fileEntry.getName().endsWith(".java")) {      
                try {
                    String name = fileEntry.getAbsolutePath();
                    
                    Reporter.log("Leyendo y Analizando: " + name);
                    
                    String content = new Scanner(fileEntry).useDelimiter("\\Z").next();
                    javacompiler.compiler.Analyze(content, p1);
                                        
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    Reporter.err("Error al abrir el archivo.");
                } catch (Exception ex){
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    Reporter.err("Error de compilacion: " + ex.getMessage());
                }
            }
        }        
    }
    
    public static String listenToClient(ObjectInputStream input) throws IOException, ClassNotFoundException{
            
        Reporter.log("** Cliente conectado. Iniciando lectura de proyecto.");

        String data =  (String) input.readObject();
        
        if (data == null || data.length() == 0){
            Reporter.err("Archivos del cliente incorrectos.");
            return "";
        }
        
        String proyectSeparator = " ##eop## ";

        String[] proyectsData = data.split(proyectSeparator);

        if (proyectsData.length != 2){
            Reporter.err("Solo se puede comparar dos proyectos.");
            return "";
        }
        
        Reporter.log("Analizando proyecto 1...");
        Proyect p1 = new Proyect();
        readFiles(new File(proyectsData[0]), p1);
        Reporter.log("Analizando proyecto 2...");
        Proyect p2 = new Proyect(); 
        readFiles(new File(proyectsData[1]), p2);

        String JSON = analyzer.getRESULTString(p1, p2);

        Reporter.log("Escribiendo estructura JSON a cliente.");
                        
        return JSON;
    }
    
    public static void writeToClient(ObjectOutputStream output, String message) throws IOException{
        output.writeObject(message);
    }
    
    public static void main(String[] args){
        
        if (args.length < 1) return;
 
        int port = Integer.parseInt(args[0]);
        
        ServerMainWindow smw = new ServerMainWindow();
                
        
        
        smw.setVisible(true);
        
        Reporter.setConsole(smw.getConsole());
        
        try (ServerSocket serverSocket = new ServerSocket(port)){
            
            smw.addWindowListener(new java.awt.event.WindowAdapter() {
                
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    try {
                        serverSocket.close();
                        System.exit(0);
                    } catch (IOException ex) {
                        Reporter.err("Error al cerrar aplicacion: " + ex.getMessage());
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            });
            
            while(true){         
                
                Reporter.log("Server escuchando en el puerto: " + port);
                
                try (Socket client = serverSocket.accept()){
                    String json = listenToClient(new ObjectInputStream(client.getInputStream()));
                    
                    ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                    writeToClient(oos, json);
                    
                    oos.flush();
                    oos.close();
                }
                catch (IOException ex){
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    Reporter.err("Error de entrada/salida: " + ex.getMessage());
                }
                catch (ClassNotFoundException ex){
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    Reporter.err("Error de server: " + ex.getMessage() );
                } 
                catch (Exception ex){
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    Reporter.err("Error: " + ex.getMessage());
                }
                finally {
                    Reporter.log("Cerrando conexión con cliente.");
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            Reporter.err("Error de server: " + ex.getMessage());
        } catch (Exception ex){
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            Reporter.err("Error: " + ex.getMessage());
        }
    }

}
