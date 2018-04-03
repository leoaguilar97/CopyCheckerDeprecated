package proyectdata;

import java.util.Objects;

/**
 *
 * @author Leonel Aguilar
 */
public class VarInfo {
    private String type;
    private String name;
    private String func_name;
    private String class_name;
    private boolean checkable = true;

    /**
     * Get the value of checkable
     *
     * @return the value of checkable
     */
    public boolean isCheckable() {
        return checkable;
    }

    /**
     * Set the value of checkable
     *
     * @param checkable new value of checkable
     */
    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }


    public VarInfo(String type, String name, String func_name, String class_name) {
        this.type = type;
        this.name = name;
        this.func_name = func_name;
        this.class_name = class_name;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunc_name() {
        return func_name;
    }

    public void setFunc_name(String func_name) {
        this.func_name = func_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.func_name);
        hash = 79 * hash + Objects.hashCode(this.class_name);
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
        final VarInfo other = (VarInfo) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.func_name, other.func_name)) {
            return false;
        }
        return Objects.equals(this.class_name, other.class_name);
    }

    

    public String toJson() {
        return String.format("{\"Nombre\":\"%s\",\"Tipo\":\"%s\",\"función\":\"%s\",\"Clase\":\"%s\"}", 
            name,
            type,
            func_name,
            class_name
        );
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    
    
}
