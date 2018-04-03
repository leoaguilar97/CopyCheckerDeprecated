/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.html.tag;

import java.util.ArrayList;

/**
 *
 * @author leoag
 */
public class Tag {

    private String name;
    private boolean isText;
    private boolean isBreakLine;
    
    private ArrayList<Property> properties;
    private final ArrayList<Tag> innerTags;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
        this.innerTags = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.isText = false;
        this.isBreakLine = false;
    }
    
    public void addProperty(String name, String value){
        this.properties.add(new Property(name, value));
    }

    public boolean isIsText() {
        return isText;
    }

    public void setIsText(boolean isText) {
        this.isText = isText;
    }       

    public boolean isIsBreakLine() {
        return isBreakLine;
    }

    public void setIsBreakLine(boolean isBreakLine) {
        this.isBreakLine = isBreakLine;
    }
    
    public String getAsHtml(){
        String result;
        if (isText){
            return name;
        } else if (isBreakLine){
            result = "<%s/>";
            return String.format(result, name);
        } else {
            String innerTagsValue = "";
            
            if (innerTags.size() > 0){
                innerTagsValue = innerTags.stream().map((tag) -> "\n" + tag.getAsHtml()).reduce(innerTagsValue, String::concat);
            }
            
            String propertyValues;
            
            if (properties.isEmpty()){
                propertyValues = "";
            } else {
                ArrayList<String> props = new ArrayList<>();
                
                properties.forEach((p) -> {
                    props.add(p.asHtml());
                });
                
                propertyValues = String.format("style=\"%s\"", String.join("; ", props));
            }
            
            result = "<%s %s>%s</%s>";
            return String.format(result, name, propertyValues, innerTagsValue, name);
            
        }
    }

    public void addTag(Tag tag) {
        innerTags.add(tag);
    }
    
    public void addBr(){
        Tag brTag = new Tag("br");
        brTag.setIsBreakLine(true);
        innerTags.add(brTag);
    }

    public void addHr(){
        Tag hrTag = new Tag("hr");
        hrTag.setIsBreakLine(true);
        innerTags.add(hrTag);    
    }
    
    public void addText(String name){
        Tag txt = new Tag(name);
        txt.setIsText(true);
        innerTags.add(txt);
    } 

    public boolean hasProperty(String name){
        
        return properties.stream().anyMatch((p) -> (p.getName().equals(name)));
    }
    
}
