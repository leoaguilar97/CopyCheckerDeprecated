package ui;

import javax.swing.text.Caret;

/**
 * Editor of CPReport
 * @author Leonel Aguilar
 */
public class Editor {
    
    private String path;
    private String code;
    private String name;
    
    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     *
     * @return Código del editor
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code Código a colocar
     */
    public void setCode(String code) {
        this.code = code;
    }
    

    /**
     * @return Nombre del editor
     */
    public String getName() {
        return name;
    }

    /**
     * @param name nuevo nombre del editor
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Crea un nuevo editor de texto
     * @param path
     */
    public Editor(String path){
        this.path = path;
        this.code = "";
        this.name = path;
    }
    
    /**
     * Crea un nuevo editor 
     * @param path PATH del editor
     * @param name Nombre del editor
     */
    public Editor(String path, String name) {
        this.path = path;
        this.code = "";
        this.name = name;
    }
}
