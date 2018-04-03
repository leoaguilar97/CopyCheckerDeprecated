/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporter;

/**
 *
 * @author leoag
 */
public class Error {
    
    private int line;
    
    private int number;

    public Error(int number, int line, int column, String message, String type) {
        this.line = line;
        this.number = number;
        this.column = column;
        this.message = message;
        this.type = type;
    }
    
    /**
     * Get the value of number
     *
     * @return the value of number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set the value of number
     *
     * @param number new value of number
     */
    public void setNumber(int number) {
        this.number = number;
    }
    
    /**
     * Get the value of line
     *
     * @return the value of line
     */
    public int getLine() {
        return line;
    }

    /**
     * Set the value of line
     *
     * @param line new value of line
     */
    public void setLine(int line) {
        this.line = line;
    }

    private int column;

    /**
     * Get the value of column
     *
     * @return the value of column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Set the value of column
     *
     * @param column new value of column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    private String message;

    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the value of message
     *
     * @param message new value of message
     */
    public void setMessage(String message) {
        this.message = message;
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

    public String getAsHtmlTableRow(){
        String format = "<tr> <td class=\"tg-yw4l\">%s</td> <td class=\"tg-z9dd\">%s</td> <td class=\"tg-4jb6\">%s</td> <td class=\"tg-z9dd\">%s</td> <td class=\"tg-4jb6\">%s</td> </tr>";
        return String.format(format, number, type, line, column, message);
    }

    @Override
    public String toString() {
        return "Error {" + "line=" + line + ", number=" + number + ", column=" + column + ", message=" + message + ", type=" + type + '}';
    }


}
