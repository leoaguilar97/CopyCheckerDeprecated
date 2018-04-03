package proyectdata;

import java.util.Objects;

/**
 * 
 * @author Leonel Aguilar
 */
public class ComentInfo {
    
    private String content;   
    
    private boolean checkable;

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

    
    /**
     * Get the value of content
     *
     * @return the value of content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public ComentInfo(String content) {
        this.content = content;
        this.checkable = true;
    }
    
    /**
     * Set the value of content
     *
     * @param content new value of content
     */
    public void setContent(String content) {
        this.content = content.trim();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.content);
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
        final ComentInfo other = (ComentInfo) obj;
        
        return other.getContent().equals(this.getContent());
    }

    String toJson() {
        return String.format("{\"Texto\":\"%s\"}", getContent());
    }

    @Override
    public String toString() {
        return this.toJson();
    }
    
    
}
