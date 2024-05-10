package chat.client.ui;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class TextArea extends JTextPane {
    private static final String NEW_LINE = "\n";
    private static final Font FONT = new Font("Lucida Console", Font.BOLD, 12);

    public TextArea() {
        setEditable(false);
        setPreferredSize(new Dimension(625, 400));
        setFont(FONT);
        setBackground(Color.BLACK);
    }

    public void append(String text, Color color) {
        StyledDocument doc = getStyledDocument();
        Style style = addStyle("Style", null);
        StyleConstants.setForeground(style, color);

        try {
            doc.insertString(doc.getLength(), text + NEW_LINE, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
