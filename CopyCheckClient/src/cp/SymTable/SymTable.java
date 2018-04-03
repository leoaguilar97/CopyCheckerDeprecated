package cp.SymTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import Reporter.Reporter;

/**
 * Crea una nueva tabla de simbolos
 * @author Leonel Aguilar
 */
public class SymTable {
    
    private final HashSet<SymValue> values;
    
    public SymTable() {
        values = new HashSet<>();
    }
    
    public String getValueType(Object val){
        
        
        if (val instanceof Integer){
            return "texto";
        } else if (val instanceof Double){
            return "decimal";
        } else if (val instanceof String){
            return "texto";
        } else if (val instanceof Character){
            return "caracter";
        } else if (val instanceof Boolean){
            return "booleano";
        } 
        return "desconocido";
    }
    
    public void addSymbol(String type, String name) throws Exception{
        SymValue val = new SymValue(name, type);
        if (!values.add(val)){
            throw new Exception("La variable [" + name + "] ya ha sido instanciado anteriormente.");
        }
    }
        
    public Object performOperation(Object value1, Object value2, String operator) throws Exception {
        
        if (value1 == null || value2 == null){
            throw new Exception("Referencia a una variable o valor nulo. No se puede operar.");
        }
        
        if (value1 instanceof SymValue){
            return performOperation(((SymValue) value1).getValue(), value2, operator);
        } else if (value2 instanceof SymValue){
            return performOperation(value1, ((SymValue) value2).getValue(), operator);
        }
        
        try {
            switch(operator){
                //ARITMETHICS
                case "+":                 
                    if (value1 instanceof String || value2 instanceof String){
                        return value1.toString() + value2.toString();
                    }
                    if ( value1 instanceof Integer ){
                        if (value2 instanceof Integer){
                            return (Integer)((Integer)value1 + (Integer)value2);
                        } else if (value2 instanceof Double){
                            return (Double)((Integer)value1 + (Double)value1);
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            int val2 = (Integer)value2;
                            return (Double)((Double)value1 + (Integer)val2);
                        } else if (value2 instanceof Double){
                            return (Double)((Double)value2 + (Double)value1);
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());
                    }

                case "-":
                    if (value1 instanceof Integer){
                        if (value2 instanceof Integer){
                            return (Integer)value1 - (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Double)((Double)value1 - (Double)value2);
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            return (Double)((Double)value1 - (Integer)value2);
                        } else if (value2 instanceof Double){
                            return (Double)value1 - (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());
                    }

                case "*":
                    if (value1 instanceof Integer){
                        if (value2 instanceof Integer){
                            return (Integer)value1 * (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Double)value1 * (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            return (Double)((Double)value1 * (Integer)value2);
                        } else if (value2 instanceof Double){
                            return (Double)value1 * (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());
                    }

                case "/": 
                    if (value1 instanceof Integer){
                        if (value2 instanceof Integer){
                            return (Integer)value1 / (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Double)((Integer)value1 / (Double)value2);
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            return (Double)((Double)value1 / (Integer)value2);
                        } else if (value2 instanceof Double){
                            return (Double)value1 / (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());
                    }

                case "%":
                    if (value1 instanceof Integer){
                        if (value2 instanceof Integer){
                            return (Integer)value1 % (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Double)((Integer)value1 % (Double)value2);
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            return (Double)((Double)value1 % (Integer)value2);
                        } else if (value2 instanceof Double){
                            return (Double)value1 % (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.getClass());
                        }
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());
                    }

                //BOOLEANS
                case ">":
                    if (value1 instanceof Integer) {
                        if (value2 instanceof Integer){
                            return (Integer)value1 > (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Integer)value1 > (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.toString());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            return (Double)value1 > (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Double)value1 > (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.toString());
                        } 
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());
                    }

                case "<": 
                    if (value1 instanceof Integer) {
                        if (value2 instanceof Integer){
                            return (Integer)value1 < (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Integer)value1 < (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.toString());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            return (Double)value1 < (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Double)value1 < (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.toString());
                        } 
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());
                    }

                case "<=":
                    if (value1 instanceof Integer) {
                        if (value2 instanceof Integer){
                            return (Integer)value1 <= (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Integer)value1 <= (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.toString());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            return (Double)value1 <= (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Double)value1 <= (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.toString());
                        } 
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());                    }

                case ">=":
                    if (value1 instanceof Integer) {
                        if (value2 instanceof Integer){
                            return (Integer)value1 >= (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Integer)value1 >= (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.toString());
                        }
                    } else if (value1 instanceof Double){
                        if (value2 instanceof Integer){
                            return (Double)value1 >= (Integer)value2;
                        } else if (value2 instanceof Double){
                            return (Double)value1 >= (Double)value2;
                        } else {
                            throw new Exception("Operando invalido: " + value2.toString());
                        } 
                    } else {
                        throw new Exception("Operando invalido: " + value1.getClass());
                    }

                case "==":
                    if (value1 instanceof Integer && value2 instanceof Integer){
                        return Objects.equals((Integer)value1, (Integer)value2);
                    } else if (value1 instanceof Double && value2 instanceof Double){
                        return Objects.equals((Integer)value1, (Integer)value2);
                    } else if (value1 instanceof String && value2 instanceof String){
                        return Objects.equals((Integer)value1, (Integer)value2);
                    } else {
                        return Objects.equals(value1, value2);
                    }

                case "!=":
                    if (value1 instanceof Integer && value2 instanceof Integer){
                        return ! Objects.equals((Integer)value1, (Integer)value2);
                    } else if (value1 instanceof Double && value2 instanceof Double){
                        return ! Objects.equals((Integer)value1, (Integer)value2);
                    } else if (value1 instanceof String && value2 instanceof String){
                        return ! Objects.equals((Integer)value1, (Integer)value2);
                    } else {
                        return ! Objects.equals(value1, value2);
                    }

                default: 
                    return null;
            }
        }
        catch(ArithmeticException ce){
            throw new Exception("Error aritmetico " + ce.getMessage());
        }
        catch(Exception ex){
            String format = "Error alrealizar la operacion [%s %s %s]: %s .";
            throw new Exception(String.format(format, value1.toString(), operator, value2.toString() + " : " , ex.getMessage()));
        }    
    }
    
    public Object performBoolOperation(Object value1, Object value2, String bool_operator) throws Exception{
        
        if (value1 instanceof SymValue){
            return performBoolOperation(((SymValue) value1).getValue(), value2, bool_operator);
        } else if (value2 instanceof SymValue){
            return performBoolOperation(value1, ((SymValue) value2).getValue(), bool_operator);
        } 
        
        switch (bool_operator){
            case "||":
                if (value1 instanceof Boolean && value2 instanceof Boolean){
                    return (Boolean) value1 || (Boolean) value2;
                } 
                break;
                
            case "&&":
                if (value1 instanceof Boolean && value2 instanceof Boolean){
                    return (Boolean) value1 && (Boolean) value2;
                } 
                break;
        }
        throw new Exception("No se pueden aplicar operadores booleanos a " + value1 + " o " + value2);
    }
    
    public Object performOperation(Object value1, String unary_operator) throws Exception{
        
        if (value1 instanceof SymValue){
            return performOperation(((SymValue) value1).getValue(), unary_operator);
        } 
        
        switch(unary_operator){
            case "-":
                if (value1 instanceof Integer){
                    return -1 * (Integer) value1;
                } else if (value1 instanceof Double){
                    return -1 * (Double) value1;
                } else {
                    String format = "Operador de negacion aritmetica (-): Valor <%s = %s>.";
                    throw new Exception(String.format(format, getValueType(value1), value1));
                }
            
            case "!":
                if (value1 instanceof Boolean){
                    return ! (Boolean) value1;
                } else {
                    String format = "Operador de Negacion (!): Tipo invalido <%s = %s>.";
                    throw new Exception(String.format(format, getValueType(value1), value1));
                }
            
            case "++":
                if (value1 instanceof Integer){
                    return (Integer) value1 + 1;
                } else if (value1 instanceof Double){
                    return (Double) value1 + 1;
                } else {
                    String format = "Operador de autoincremento (++): Valor <%s = %s>.";
                    throw new Exception(String.format(format, getValueType(value1), value1));            
                }
                
                
            case "--":
                if (value1 instanceof Integer){
                    return (Integer) value1 + 1;
                } else if (value1 instanceof Double){
                    return (Double) value1 + 1;
                } else {
                    String format = "Operador de autoincremento (--): Valor <%s = %s>.";
                    throw new Exception(String.format(format, getValueType(value1), value1));            
                }
            
            default:
                String format = "Operador desconocido: (%s) aplicado a <%s = %s>.";
                throw new Exception(String.format(format, unary_operator, getValueType(value1), value1));
        }
    }
    
    public void setValue(SymValue val, Object value) throws Exception{
        if (val != null){
            switch(val.getType()){
                case "entero":
                    if (value instanceof Integer){
                        val.setValue(value);
                        return;
                    } 
                    break;
                    
                case "decimal":
                    if (value instanceof Double){
                        val.setValue(value);
                        return;
                    } 
                    break;
                    
                case "texto":
                    if (value instanceof String){
                        val.setValue(value);
                        return;
                    }
                    break;
                    
                case "caracter":
                    if (value instanceof Character){
                        val.setValue(value);
                        return;
                    }
                    break;
                
                case "booleano":
                    if (value instanceof Boolean){
                        val.setValue(value);
                        return;
                    }
                    break;
            }
            String format = "Variable %s: No se puede asignar <%s [ %s ]> a <%s>.";
            throw new Exception(String.format(format, val, getValueType(value), value == null ? "null" : value.toString(), val.getType()));
        } 
    }
    
    public void setValue(String symName, Object value, String eq_operator) throws Exception{
        SymValue val = null;
        
        for(SymValue v: values){
            if (v.getName().equals(symName)){
                val = v;
                break;
            }        
        } 
        
        if (val == null){
            throw new Exception("No se ha declarado el simbolo [" + symName + "].");
        }
                
        switch(eq_operator){
            case "=":
                setValue(val, value);
                break;
            case "+=":
                setValue(val, performOperation(val, value, "+"));
                break;
            case "-=":
                setValue(val, performOperation(val, value, "-"));
                break;
            case "*=":
                setValue(val, performOperation(val, value, "*"));
                break;
            case "/=":
                setValue(val, performOperation(val, value, "/"));
                break;
            case "%=":
                setValue(val, performOperation(val, value, "%"));
                break;
        }
    }
    
    public Object getValue(String symbolName) throws Exception{
        for(SymValue v: values) {
            if (v.getName().equals(symbolName)){
                return v.getValue();
            }
        }
        throw new Exception("No se puede obtener el valor de [" + symbolName + "]. Asegurese de haberlo instanciado.");
    }
    
    public String getSymbols(){
        ArrayList<String> result = new ArrayList<>();
        values.forEach((v) -> {
            result.add(v.toString());
        });
        return String.join(", ", result);
    }
    
}
