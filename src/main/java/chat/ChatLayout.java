package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatLayout {
    private final JFrame frame = new JFrame();
    private final JTextArea chatArea = new JTextArea();
    private final JTextField messageField = new JTextField();
    private final JButton sendButton = new JButton("Send");

    private String username;

    public ChatLayout() {
        initialize();
        buildForm();
    }

    public void initialize() {
        username = JOptionPane.showInputDialog(frame, "Enter your username:");
        if (username == null || username.trim().isEmpty()) {
            System.exit(0); // Exit if username is not provided
        }
    }

    public void buildForm() {
        frame.setTitle(username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        chatArea.setEditable(false);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void sendActionListener(ActionListener event) {
        sendButton.addActionListener(event);
        messageField.addActionListener(event); // Think about this
    }

    public void updateChatArea(String messageText) {
        SwingUtilities.invokeLater(() -> chatArea.append(messageText + "\n"));
    }

    public String getMessageInput() {
        return messageField.getText();
    }

    public String getUsername() {
        return username;
    }

    public void closeWindow() {
        frame.dispose();
    }

    public void clearMessageInput() {
        messageField.setText("");
    }


}

