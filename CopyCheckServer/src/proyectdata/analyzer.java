package proyectdata;

import java.util.ArrayList;
import java.util.HashSet;
import ui.Reporter;

/**
 *
 * @author Leonel Aguilar
 */
public class analyzer {
    
    private static double CompareComents(ArrayList<ComentInfo> p1, ArrayList<ComentInfo> p2, JsonObject jo ){
        
        int total = p1.size() + p2.size();
        
        if (total == 0){
            return 0;
        }
        
        ArrayList<ComentInfo> tmp1 = new ArrayList<>();
        ArrayList<ComentInfo> tmp2 = new ArrayList<>();
        
        
        tmp1.addAll(p1);
        tmp2.addAll(p2);
        
        int finalSize = tmp1.size() + tmp2.size();
        
        HashSet<ComentInfo> repeated = new HashSet<>();
        
        int repeatedCount = 0;
        
        for(ComentInfo info1: tmp1){
            if (tmp2.isEmpty()) break;
            for(ComentInfo info2: tmp2){
                
                if(info2.equals(info1) && info1.isCheckable() && info2.isCheckable() ){
                    info1.setCheckable(false);
                    info2.setCheckable(false);
                    repeated.add(info1);     
                    repeatedCount++;
                    break;
                }
            }
        }
        
        jo.setRepeatedComents(repeated);
        
        return ((double) (repeatedCount * 2)) / (finalSize);
        
    }
    
    private static double CompareClasses(ArrayList<ClassInfo> p1, ArrayList<ClassInfo> p2, JsonObject jo){
        
        ArrayList<ClassInfo> tmp1 = new ArrayList(), tmp2 = new ArrayList<>();
        HashSet<ClassInfo> repeated = new HashSet<>();
        
        tmp1.addAll(p1);
        tmp2.addAll(p2);
        
        for(ClassInfo info1: tmp1){
            if (tmp2.isEmpty()) break;            
            tmp2.stream().filter((info2) -> (info1.equals(info2))).forEachOrdered((_item) -> {
                repeated.add(info1);
            });
        }
        
        jo.setRepeatedClasses(repeated);
        
        int total = p1.size() + p2.size();
               
        return total == 0 ? 0 : ((double) repeated.size() * 2) / (total);
    }
    
    private static double CompareFunctions(ArrayList<ClassInfo> p1, ArrayList<ClassInfo> p2, JsonObject jo){
        
        HashSet<FunctInfo> f1 = new HashSet<>();
        HashSet<FunctInfo> f2 = new HashSet<>();
        
        HashSet<FunctInfo> repeated = new HashSet<>();
        
        p1.forEach((info) -> {
            info.getFunctions().forEach((functInfo) -> {
                f1.add(functInfo);
            });
        });
        
        p2.forEach(info -> {
            info.getFunctions().forEach(functInfo -> {
                f2.add(functInfo);
            });            
        });
        
        f2.forEach(info -> {
            if (!f1.add(info)){
                repeated.add(info);
            }
        });
        
        jo.setRepeatedFuncts(repeated);
        
        int total = f1.size() + f2.size();
        
        return total == 0 ? 0 : ((double) repeated.size() * 2) / (total);
    }
    
    private static double CompareVars(ArrayList<ClassInfo> p1, ArrayList<ClassInfo> p2, JsonObject jo){
        ArrayList<VarInfo> v1 = new ArrayList<>();
        ArrayList<VarInfo> v2 = new ArrayList<>();
        
        ArrayList<VarInfo> repeatedVars = new ArrayList<>();
        
        p1.forEach((info) -> {
            info.getVars().forEach(item -> {
                v1.add(item);
            });
        });        
        
        p2.forEach((info) -> {
            info.getVars().forEach(item -> {
                v2.add(item);
            });
        });  
        
        int total = v1.size() + v2.size();
        
        for(VarInfo v1Inf: v1){
            if (!v1Inf.isCheckable()) continue;
            v2.stream().filter((v2Inf) -> !(!v2Inf.isCheckable())).filter((v2Inf) -> (v1Inf.getName().equals(v2Inf.getName()) && v1Inf.getType().equals(v2Inf.getType()))).map((v2Inf) -> {
                repeatedVars.add(v1Inf);
                repeatedVars.add(v2Inf);
                return v2Inf;
            }).forEachOrdered((v2Inf) -> {
                v2Inf.setCheckable(false);
            });
            v1Inf.setCheckable(false);
        }
        
        HashSet<VarInfo> finalSet = new HashSet<>();
        
        finalSet.addAll(repeatedVars);
        
        jo.setRepeatedVars(finalSet);
        
        return total == 0 ? 0 : ((double) repeatedVars.size()) / (total) ;      
        
    }
    
    public static String getRESULTString(Proyect p1, Proyect p2){
        JsonObject json = new JsonObject();
                
        double score;
        
        try {
            double cs = CompareComents(p1.getComents(), p2.getComents(), json) * 0.25;
          
            double cls = CompareClasses(p1.getClasses(), p2.getClasses(), json)* 0.25;
        
            double fs = CompareFunctions(p1.getClasses(), p2.getClasses(), json)* 0.25;
        
            double vs = CompareVars(p1.getClasses(), p2.getClasses(), json)* 0.25;
        
            score = (cs + cls + fs + vs);
        
        } catch (Exception ex){
            Reporter.err(ex.getMessage());
            score = 0;
        }
        
        return json.getJsonString(score);
    }
}
