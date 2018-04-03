package cp.SymTable;

import java.util.Objects;

/**
 *
 * @author leoag
 */
public class SymValue {
    
    private String name;

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

    private Object value;

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    private String type;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(String type) {
        this.type = type.toLowerCase();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.value);
        hash = 97 * hash + Objects.hashCode(this.type);
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
        final SymValue other = (SymValue) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public SymValue(String name, Object value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public SymValue(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public SymValue(String name) {
        this.name = name;
    }
        
    public SymValue() {
        this.name = "";
        this.value = "";
        this.type = "";
    }    

    @Override
    public String toString() {
        return "{" + "nombre= " + name + ", valor= " + value + ", tipo= " + type + '}';
    }
        
}
