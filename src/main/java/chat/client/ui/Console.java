package chat.client.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.OutputStream;
import java.io.PrintStream;

public class Console {
    private final JFrame frame;
    private final ColorPane textPane;
    private final JScrollPane scroll;
    private final JTextField messageField;

    public Console() {
        frame = new JFrame("CONSOLE");
        textPane = new ColorPane();
        textPane.setEditable(false);
        textPane.setPreferredSize(new Dimension(625, 400));
        textPane.setFont(new Font("Lucida Console", Font.BOLD, 12));
        textPane.setBackground(Color.BLACK);
        scroll = new JScrollPane(textPane);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        messageField = new JTextField(20);
        messageField.addActionListener(e -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                sendMessage(message);
                messageField.setText("");
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);

        addDocumentListener();
        redirectOutput();

        frame.add(scroll);
        frame.setSize(100, 600);
        frame.pack();
        frame.setVisible(false);
    }

    private void addDocumentListener() {
        textPane.getDocument().addDocumentListener(new DocumentListener() {
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

    public void sendMessage(String message) {
        appendToConsole(message, Color.WHITE); // Append the message with a newline character
        scrollToBottom();
    }

    private void appendToConsole(String message, Color color) {
        textPane.setForeground(color);
        textPane.append(message + "\n", color);
    }

    private void redirectOutput() {
        OutputStream out = createOutputStream(Color.WHITE);
        OutputStream err = createOutputStream(Color.RED);

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
        scrollToBottom();
    }

    private OutputStream createOutputStream(Color color) {
        return new OutputStream() {
            @Override
            public void write(int b) {
                textPane.append(String.valueOf((char) b), color);
            }
        };
    }

    private void scrollToBottom() {
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

    public JFrame getFrame() {
        return frame;
    }
}
