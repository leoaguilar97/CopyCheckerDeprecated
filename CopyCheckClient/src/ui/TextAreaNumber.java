package ui;

import java.awt.Rectangle;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Leonel Aguilar
 */
public class TextAreaNumber implements LineNumberModel{        
    private final JTextArea textArea;

    @Override
    public int getNumberLines() {
        return textArea.getLineCount();
    }

    @Override
    public Rectangle getLineRect(int line) {
        try {
            return textArea.modelToView(textArea.getLineStartOffset(line));
        } catch(BadLocationException e){
            return new Rectangle();
        }
    }

    public TextAreaNumber(JTextArea textArea){
        this.textArea = textArea;
    }
}
