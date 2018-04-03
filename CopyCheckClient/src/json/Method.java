/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

/**
 *
 * @author leoag
 */
class Method {
    
    private String name;

    Method() { }

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
     * @param type new value of type
     */
    public void setType(String type) {
        this.type = type;
    }

    private int params;

    /**
     * Get the value of params
     *
     * @return the value of params
     */
    public int getParams() {
        return params;
    }

    /**
     * Set the value of params
     *
     * @param params new value of params
     */
    public void setParams(int params) {
        this.params = params;
    }

    public Method(String name, String type, int params) {
        this.name = name;
        this.type = type;
        this.params = params;
    }

    @Override
    public String toString() {
        return "Method{" + "name=" + name + ", type=" + type + ", params=" + params + '}';
    }
}
