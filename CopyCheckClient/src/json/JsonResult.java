package json;

import java.util.ArrayList;

/**
 *
 * @author Leonel Aguilar
 */
public class JsonResult {
    
    private double score = 0;
    
    private final ArrayList<String> classes;
    private final ArrayList<Method> methods;    
    private final ArrayList<Var> vars;    
    private final ArrayList<String> coments;

    public JsonResult() {
        this.classes = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.vars = new ArrayList<>();
        this.coments = new ArrayList<>();
    }
       
    @Override
    public String toString() {
        return "JsonResult{" + "score=" + score + ", classes=" + classes + ", methods=" + methods + ", vars=" + vars + ", coments=" + coments + '}';
    }
    
    // SCORE
    
    public double getScore(){
        return this.score;
    }
    
    public void setScore(double score){
        this.score = score;
    }
    
    // VARIABLES
    
    void addVar(Var var) {
        vars.add(var);
    }
    
    public String getVars() {
        ArrayList<String> result = new ArrayList<>();
        
        vars.forEach((v) -> {
            result.add(v.toString());
        });
        
        return String.join(", ", result);
    }

    public String getVarName(int index) throws Exception{
        if (index < 0 || index >= vars.size()){
            throw new Exception("Obtener nombre de variable: Indice fuera de rango.");
        }
        return vars.get(index).getName();
    }
    
    public String getVarFunc(int index) throws Exception {
        if (index < 0 || index >= vars.size()){
            throw new Exception("Obtener función de variable: Indice fuera de rango.");
        }
        return vars.get(index).getName();
    }

    public String getVarType(int index) throws Exception {
        if (index < 0 || index >= vars.size()){
            throw new Exception("Obtener tipo de variable: Indice fuera de rango.");
        }
        return vars.get(index).getName();
    }

    public String getVarClass(int index) throws Exception {
        if (index < 0 || index >= vars.size()){
            throw new Exception("Obtener clase de variable: Indice fuera del rango.");
        }
        return vars.get(index).getContainerClass();
    }
    
    public String getVarNames(){
        ArrayList<String> result = new ArrayList<>();
        
        vars.forEach((var) -> {
            result.add(var.getName());
        });
        
        return String.join(",", result);
    }

    public int getVarSize() {
        return vars.size();
    }

    // METHODS
    
    void addMethod(Method method) {
        methods.add(method);
    }
    
    public String getMethods() {
        ArrayList<String> result = new ArrayList<>();
        
        methods.forEach((m) -> {
            result.add(m.toString());
        });
        
        return String.join(",", result);
    }
    
    public String getMethodName(int index) throws Exception {
        if (index < 0 || index >= vars.size()){
            throw new Exception("Obtener nombre de función: Indice fuera de rango." );
        }
        return vars.get(index).getName();
    }

    public String getMethodType(int index) throws Exception {
        if (index < 0 || index >= methods.size()){
            throw new Exception("Obtener tipo de funcion: Indice fuera de los limites.");
        } 
        return methods.get(index).getType();
    }
    
    public int getMethodParams(int index) throws Exception {
        if (index < 0 || index >= methods.size()){
            throw new Exception("Obtener parametros de funcion: Indice fuera de los limites.");
        } 
        return methods.get(index).getParams();
    }
    
    public int getMethodSize() {
        return methods.size();
    }
    
    public String getMethodNames() {
        ArrayList<String> result = new ArrayList<>();
        
        methods.forEach((m) -> {
            result.add(m.getName());
        });
        
        return String.join(", ", result);
    }
      
    // CLASSES
    
    public void addClass(String name){
        classes.add(name);
    }
    
    public String getClasses() {
        ArrayList<String> result = new ArrayList<>();
        
        classes.forEach((c) -> { result.add("{ Nombre= " + c + " }");});     
        
        return String.join(", ", result);
    }
    
    public String getClassNames() {
        return String.join(",", classes);
    }
    
    public String getClassName(int index) throws Exception {
        if (index < 0 || index >= classes.size()){
            throw new Exception("Obtener parametros de funcion: Indice fuera de los limites.");
        } 
        return classes.get(index);
    }
    
    public int getClassesSize() { 
        return classes.size();
    }
    
    // COMENTS
    
    public void addComent(String text){
        coments.add(text);
    }
    
    public String getComent(int index) throws Exception{
        if (index < 0 || index >= coments.size()){
            throw new Exception("Obtener comentario: Indice fuera de rango.");
        }
        return coments.get(index);
    }
    
    public String getComents() {
        return String.join(", ", coments);
    }

    public int getComentsSize() {
        return coments.size();
    }

}
