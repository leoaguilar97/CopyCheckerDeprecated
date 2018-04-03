package ui;

import java.awt.Rectangle;

public interface LineNumberModel {
    public int getNumberLines();
    public Rectangle getLineRect(int line);
}