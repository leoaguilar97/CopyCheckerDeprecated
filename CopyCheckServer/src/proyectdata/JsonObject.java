package proyectdata;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Leonel Aguilar
 */
public class JsonObject {
    HashSet<ComentInfo> coments = new HashSet<>();
    HashSet<ClassInfo> classes = new HashSet<>();
    HashSet<FunctInfo> functs = new HashSet<>();
    HashSet<VarInfo> vars = new HashSet<>();
    
    public void setRepeatedComents(HashSet<ComentInfo> coments) {
        this.coments = coments;
    }
    
    public void setRepeatedClasses(HashSet<ClassInfo> classes){
        this.classes = classes;
    }

    public void setRepeatedFuncts(HashSet<FunctInfo> functs){
        this.functs = functs;
    }
    
    public void setRepeatedVars(HashSet<VarInfo> vars) {
        this.vars = vars;
    }

    public String getJsonString(double score) {
        String json = "{\"score\":[%,.2f],\"clases\":[%s],\"variables\":[%s],\"metodos\":[%s],\"comentarios\":[%s]}";
        
        ArrayList<String> jsonValues = new ArrayList<>();
        
        int divisor = 0;
        
        if (!classes.isEmpty()){
        
            classes.forEach((info) -> {
                jsonValues.add(info.toJson());
            });
            
            divisor++;
        
        }      
        
        String classesJson = String.join(",", jsonValues);
        
        jsonValues.clear();

        if (!functs.isEmpty()){
            functs.forEach((info) -> {
                jsonValues.add(info.toJson());
            });
            
            divisor++;            
        }
             
        String functsJson = String.join(",", jsonValues);
        
        jsonValues.clear();
        
        if (!vars.isEmpty()){
            vars.forEach((info) -> {
                jsonValues.add(info.toJson());
            });
            
            divisor++;
        }
                
        String varsJson = String.join(",", jsonValues);
        
        jsonValues.clear();
        
        if(!coments.isEmpty()){
            coments.forEach(info -> {
                jsonValues.add(info.toJson());
            });
            
            divisor++;        
        }        
        
        String comentsJson = String.join(",", jsonValues);
        
        json = String.format(json, score, classesJson, varsJson, functsJson, comentsJson);
        
        return json;
        
    }
    
}
