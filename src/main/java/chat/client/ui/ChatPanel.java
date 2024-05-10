package chat.client.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.OutputStream;
import java.io.PrintStream;

public class ChatPanel extends JPanel {
    private final TextArea textArea = new TextArea();
    private final JScrollPane scroll = new JScrollPane(textArea);

    public ChatPanel() {
        setLayout(new BorderLayout());

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);

        addDocumentListener();
        addAdjustmentListener();
    }

    private void addDocumentListener() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                scrollToBottom();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void addAdjustmentListener() {
        JScrollBar verticalBar = scroll.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    public void updateChatArea(String message) {
        updateChatArea(message, Color.WHITE);
    }

    public void updateChatArea(String message, Color color) {
        textArea.append(message, color);
        scrollToBottom();
    }

    public void scrollToBottom() {
        JScrollBar verticalBar = scroll.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
    }

}
