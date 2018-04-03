package proyectdata;

import java.util.ArrayList;

/**
 * 
 * @author Leonel Aguilar
 */
public class Proyect {
    
    private final ArrayList<ClassInfo> classes;
    private final ArrayList<ComentInfo> coments;
    
    private ClassInfo currClassInfo;
    private FunctInfo currFunctInfo;

    public ArrayList<ClassInfo> getClasses() {
        return classes;
    }

    public ArrayList<ComentInfo> getComents() {
        return coments;
    }
    
    /**
     *
     */
    public Proyect() {
        classes = new ArrayList<>();
        coments = new ArrayList<>();
        currClassInfo = null;
        currFunctInfo = null;
    }  
    
    /**
     *
     * @param className
     */
    public void addClass(String className){
        ClassInfo ci = new ClassInfo(className);
        currClassInfo = ci;
    }
    
    /**
     *
     * @param name
     * @param returnType
     */
    public void addFunction(String name, String returnType){
        if (currClassInfo != null){
            FunctInfo fi = new FunctInfo(name, returnType); 
            currFunctInfo = fi;
        }
    }
    
    /**
     *
     * @param type
     * @param name
     */
    public void addParameter(String type, String name){
        if (currFunctInfo != null){
            currFunctInfo.addParam(new ParamInfo(type, name));
        }
    }
    
    public void addProperty(String type, String varName){
        if (currClassInfo != null){
            currClassInfo.addVar(type, varName);
        }
    }
    
    /**
     *
     * @param type
     * @param varName
     */
    public void addVar(String type, String varName){
        if (currClassInfo != null){
            if (currFunctInfo != null){
                currClassInfo.addVar(type, varName, currFunctInfo.getName());
            }
            else {
                currClassInfo.addVar(type, varName);              
            }                  
        }
    }
    
    /**
     *
     */
    public void resetClass(){
        this.classes.add(currClassInfo);
        currClassInfo = null;
    }
    
    /**
     *
     */
    public void resetCurrFunct(){
        currClassInfo.addFunction(currFunctInfo);
        currFunctInfo = null;
    }
    
    /**
     *
     * @param content
     */
    public void addComent(String content){
        coments.add(new ComentInfo(content));
    }
        
}
