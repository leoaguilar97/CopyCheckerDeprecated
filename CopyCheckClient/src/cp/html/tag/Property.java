/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.html.tag;

import java.util.Objects;

/**
 *
 * @author leoag
 */
public class Property {
    String name;
    String value;

    public Property(String name, String value) {
        this.name = name.toLowerCase();
        this.value = value.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Property other = (Property) obj;
        return Objects.equals(this.name, other.name);
    }

    public String getHtmlValue(){
        switch(value){
            case "rojo": 
                return "rgb(139, 0, 0)";
            case "amarillo":
                return "#CCCC00";
            case "azul":
                return "#4682B4";
            case "morado":
                return "#800080";
            case "verde":
                return "#008000";
            case "gris":
                return "#808080";
            case "anaranjado":
                return "#FFA500";
            case "izquierda":
                return "left";
            case "derecha":
                return "right";
            case "centrado":
                return "center";
            default:
                return value;
        }
    }
    
    public String getHtmlPropName(){
        switch(name){
            case "color":
                return "background-color";
            case "textcolor":
                return "color";
            case "align":
                return "text-align";
            case "font":
                return "font-family";
            default:
                return name;
        }
    }
    
    public String asHtml(){
        String result = "%s: %s";
                
        return String.format(result, getHtmlPropName(), getHtmlValue());
    }
}
