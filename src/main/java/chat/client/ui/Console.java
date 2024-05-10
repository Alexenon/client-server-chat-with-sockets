package chat.client.ui;

import javax.swing.*;
import java.awt.*;

public class Console {
    private final JFrame frame;
    private final ChatPanel chatPanel;
    private final JTextField messageField;

    public Console() {
        frame = new JFrame("CONSOLE");
        chatPanel = new ChatPanel();

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

        // Add chat panel with scroll to frame
        frame.add(chatPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void sendMessage(String tetx) {
        chatPanel.sendMessage(tetx);
    }

    public void sendMessage(String text, Color color) {
        chatPanel.sendMessage(text, color);
    }

}
