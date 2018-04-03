package json;

import java.util.Stack;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Leonel Aguilar
 */
public class JsonVisualizer {
    
    private JTree view;
    private Stack<DefaultMutableTreeNode> nodes;
    private JsonResult jr;
    
    boolean readingScore = false;
    boolean readingClasses = false;
    boolean readingVars = false;
    boolean readingMethods = false;
    boolean readingComents = false;
    
    boolean readingType = false;
    boolean readingFunction = false;
    boolean readingClassName = false;
    boolean readingParameters = false;
    
    String currentClass;
    String currentComent;
    Var currentVar;
    Method currentMethod;
       
    private DefaultMutableTreeNode createNode(String msg){
        return new DefaultMutableTreeNode(msg);
    }
    
    /**
     *
     */
    public JsonVisualizer(){
        super();
    }
    
    /**
     *
     * @param nodeText
     */
    public void analyzeNodeText(String nodeText){
        switch (nodeText.toLowerCase()) {
            case "score":
                readingScore = true;   
                readingClasses = false;
                readingVars = false;
                readingMethods = false;
                readingComents = false;
                break;
            case "clases":
                readingScore = false;   
                readingClasses = true;
                readingVars = false;
                readingMethods = false;
                readingComents = false;
                
                currentClass = "";
                break;
            case "variables":
                readingScore = false;   
                readingClasses = false;
                readingVars = true;
                readingMethods = false;
                readingComents = false;   
                
                currentVar = new Var();
                break;
            case "metodos":
                readingScore = false;   
                readingClasses = false;
                readingVars = false;
                readingMethods = true;
                readingComents = false;
                
                currentMethod = new Method();
                break;
            case "comentarios":
                readingScore = false;   
                readingClasses = false;
                readingVars = false;
                readingMethods = false;
                readingComents = true;
                
                currentComent = "";
                break;
            case "tipo": 
                readingType = true;
                break;
            case "función":
                readingFunction = true;
                break;
            case "clase":
                readingClassName = true;
                break;
            case "parametros":
                readingParameters = true;
                break;
        }
    }
    
    /**
     *
     * @param child
     */
    public void analyzeChildText(String child){
        if (readingScore){
            jr.setScore(Double.parseDouble(child));
            readingScore = false;
        } else if (readingType){
            if (readingVars){
                currentVar.setType(child);
            } else if (readingMethods){
                currentMethod.setType(child);
            }
            readingType = false;
        } else if (readingFunction) {
            if (readingVars){
                currentVar.setFunction(child);
            } 
            readingFunction = false;
        } else if (readingClassName){
            if (readingVars){
                currentVar.setContainerClass(child);
            }
            readingClassName = false;
        } else if (readingParameters){
            if (readingFunction){
                currentMethod.setParams(Integer.parseInt(child));
            }
            readingParameters = false;
        }
    }
    
    public JScrollPane createJsonView(String json){
                
        nodes =  new Stack<>(); 
        nodes.add(createNode("Json"));
        jr = new JsonResult();
        
        compiler.Analyze(json, this);
        
        if (!nodes.isEmpty()) {            
            view = new JTree(nodes.pop()); 
            return new JScrollPane(view);
        }
        
        return new JScrollPane();
    }
          
    /**
     *
     * @param nodeText
     */
    public void addNode(String nodeText){
        analyzeNodeText(nodeText);        
        nodes.add(createNode(nodeText));
    } 
    
    /**
     *
     * @param child
     */
    public void addChild(String child){
        if (!nodes.isEmpty()){
            analyzeChildText(child);
            nodes.peek().add(createNode(child));
        }
    }
    
    /**
     *
     * @param save
     */
    public void closeNode(boolean save){
        if (!nodes.isEmpty()){
            DefaultMutableTreeNode pop = nodes.pop();
            //save variable or function to JsonResult
            if (save) {
                if (readingVars){
                    jr.addVar(currentVar);
                    currentVar = new Var();
                } else if (readingMethods){
                    jr.addMethod(currentMethod);  
                     currentMethod = new Method();            
                } 
            }
            //add node to last tree
            if (!nodes.isEmpty()){
                nodes.peek().add(pop);
            }
        }
    }

    /**
     *
     * @param newName
     */
    public void changeCurrentNodeName(String newName) {
        if (!nodes.isEmpty()){
            DefaultMutableTreeNode peek = nodes.peek();
            //populate change
            peek.setUserObject(newName);
            //Set name of class, variable, coment or function.
            if (readingClasses){
                jr.addClass(newName);
            } else if (readingVars){
                currentVar.setName(newName);
            } else if (readingMethods){
                currentMethod.setName(newName);
            } else if (readingComents){
                jr.addComent(newName);
            }
        }
    }
    
    /**
     *
     * @return
     */
    public JsonResult getJsonResult(){
        return this.jr;
    }

}
