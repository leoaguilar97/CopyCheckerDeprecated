package cp.html;

import cp.html.tag.Tag;
import java.util.Stack;

/**
 * Generador de HTML
 * @author Leonel Aguilar
 */
public class HtmlCreator {
    public String final_html;
    public Stack<Tag> tags; 

    /**
     * Crea un nuevo generador de html
     */
    public HtmlCreator () {
        this.tags = new Stack<>();
        this.final_html = "";
    }
    
    /**
     * Anade una etiqueta HTMl
     * @param name etiqueta a anadir
     */
    public void addTag(String name){
        Tag tag = new Tag(name);
        
        if (!tags.isEmpty()) {
            tags.peek().addTag(tag);
        }
        
        tags.push(tag);
    }
    
    /**
     * Anade un nuevo salto de linea
     */
    public void addBr(){
        if (tags.isEmpty()){ return; }
        
        tags.peek().addBr();
    }
    
    /**
     * Anade un nuevo separador de linea
     */
    public void addHr(){
        if (tags.isEmpty()){ return; }
        
        tags.peek().addHr();
    }
    
    /**
     * Anade un texto al html
     * @param text texto a agregar
     */
    public void addText(String text){
        if (tags.isEmpty()){ return; }
        
        tags.peek().addText(text);
    }
    
    /**
     * Anade una propiedad al tag agregado actual
     * @param name nombre de la propiedad
     * @param value valor de la propiedad
     * @throws Exception 
     */
    public void addProperty(String name, String value) throws Exception{
        if (tags.isEmpty()){ return; }
        Tag current = tags.peek();
        
        if (current.hasProperty(name)){
            String format = "Asignacion de propiedad HTML: Error <%s> ha sido ya declarado para la etiqueta <%s>.";
            throw new Exception(String.format(format, name, current.getName()));
        } else {
            current.addProperty(name, value);
        }
    }
    
    /**
     * Cierra el tag actual
     */
    public void closeTag(){
        if (!tags.isEmpty()){
            Tag tag = tags.pop();
            
            if (tags.isEmpty()){
                final_html += tag.getAsHtml();
            }
        }
    }
        
    /**
     * Obtiene el HTML final
     * @return
     */
    public String getFinalHtml(){
        return final_html;
    }
}
