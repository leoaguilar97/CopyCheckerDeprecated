package proyectdata;

import java.util.Objects;

/**
 *
 * @author Leonel Aguilar
 */
public class ParamInfo {
    private String type;
    private String name;

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

    public ParamInfo(String type, String name) {
        this.type = type;
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.type);
        hash = 23 * hash + Objects.hashCode(this.name);
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
        final ParamInfo other = (ParamInfo) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "ParamInfo{" + "type=" + type + ", name=" + name + ", checkable=" + checkable + '}';
    }
}
