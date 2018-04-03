package proyectdata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author Leonel Aguilar
 */
public class FunctInfo {
    private String name;
    private String returnType;
    private final HashSet<ParamInfo> params;

    public FunctInfo(String name, String returnType) {
        this.name = name;
        this.returnType = returnType;
        params = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType.equals("") ? "void" : returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public HashSet<ParamInfo> getParams() {
        return params;
    }
    
    public void addParam(ParamInfo param) {
        params.add(param);
    }    

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

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.returnType);
        
        Iterator it = this.params.iterator();
        
        while(it.hasNext()){
            hash = 67 * hash + Objects.hash((ParamInfo) it.next());
        }
        
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
        final FunctInfo other = (FunctInfo) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.returnType, other.returnType)) {
            return false;
        }
        
        if (this.params.size() != other.params.size()){
            return false;
        }
        
        Iterator i1 = this.params.iterator();
        Iterator i2 = other.params.iterator();
        
        while(i1.hasNext() && i2.hasNext()){
            if (!i1.next().equals(i2.next())){
                return false;
            }
        }
        
        return true;
        
    }

    @Override
    public String toString() {
        return this.toJson();
    }
    
    public String toJson() {
        return String.format(
            "{\"Nombre\":\"%s\",\"Tipo\":\"%s\",\"Parametros\":\"%d\"}", 
            name, 
            getReturnType(), 
            params.size()
        );
    }
}
