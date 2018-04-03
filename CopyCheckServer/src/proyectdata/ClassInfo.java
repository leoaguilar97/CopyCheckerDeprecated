package proyectdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Leonel Aguilar
 */
public class ClassInfo {
    
    private String className;
    
    private final HashSet<FunctInfo> functions;
    private final ArrayList<VarInfo> vars;    
    
    public void addFunction(FunctInfo functInfo) {
        functions.add(functInfo);     
    }
        
    public void addVar(String type, String varname, String funcName){
        VarInfo varInfo = new VarInfo(type, varname, funcName, className);
        vars.add(varInfo);
    }  
    
    public void addVar(String type, String varname){
        vars.add(new VarInfo(type, varname, "", className));
    }
    
    public ClassInfo(String className) {
        this.className = className;
        this.functions = new HashSet<>();
        this.vars = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int hashCode() {
        int hash = 5;        
        hash = 31 * hash + Objects.hashCode(this.className) + functions.hashCode();
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
        
        final ClassInfo other = (ClassInfo) obj;
        if (!Objects.equals(this.className, other.className)) {
            return false;
        }
        
        return compareFunctions(other.functions);
    }   
    
    public boolean compareFunctions(HashSet<FunctInfo> functs){
        boolean canContinue = false;
        for(FunctInfo funct1: this.functions ) {
            for(FunctInfo funct2: functs){
                if (funct1.getName().equals(funct2.getName())){
                    canContinue = true;
                    break;
                }
            }
            if (!canContinue) {
                return false;
            }
            canContinue = false;
        }
        return true;
    }
    
    public HashSet<FunctInfo> getFunctions(){
        return functions;
    }
    
    public ArrayList<VarInfo> getVars(){
        return vars;
    }

    public String toJson() {
        return String.format("{\"Nombre\": \"%s\"}", className);        
    }    
}
