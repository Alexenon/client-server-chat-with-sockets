package chat.client.ui;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ColorPane extends JTextPane {
    public void append(String text, Color color) {
        StyledDocument doc = getStyledDocument();
        Style style = addStyle("Style", null);
        StyleConstants.setForeground(style, color);

        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
