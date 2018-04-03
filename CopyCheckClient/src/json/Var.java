package json;

/**
 *
 * @author Leonel Aguilar
 */
class Var {
    
    private String name;

    Var() { }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    private String type;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param tipo new value of type
     */
    public void setType(String type) {
        this.type = type;
    }

    private String function;

    /**
     * Get the value of function
     *
     * @return the value of function
     */
    public String getFunction() {
        return function;
    }

    /**
     * Set the value of function
     *
     * @param function new value of function
     */
    public void setFunction(String function) {
        this.function = function;
    }

    private String containerClass;

    /**
     * Get the value of containerClass
     *
     * @return the value of containerClass
     */
    public String getContainerClass() {
        return containerClass;
    }

    /**
     * Set the value of containerClass
     *
     * @param containerClass new value of containerClass
     */
    public void setContainerClass(String containerClass) {
        this.containerClass = containerClass;
    }

    public Var(String name, String tipo, String function, String containerClass) {
        this.name = name;
        this.type = tipo;
        this.function = function;
        this.containerClass = containerClass;
    }

    @Override
    public String toString() {
        return "Var{" + "name=" + name + ", tipo=" + type + ", function=" + function + ", containerClass=" + containerClass + '}';
    }
}
