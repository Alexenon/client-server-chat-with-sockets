package chat.client.ui;

import chat.utils.StatusCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatLayout {
    private final JFrame frame = new JFrame();
    private final ChatPanel chatPanel = new ChatPanel();
    private final JTextField messageField = new JTextField();
    private final JCheckBox encryptCheckbox = new JCheckBox("Encrypt");
    private final JButton sendButton = new JButton("Send");

    private String username;
    private boolean isClosed;

    public ChatLayout() {
        isClosed = false;
        username = UsernameInputDialog.getUsername(frame);
        buildForm();
    }

    public void buildForm() {
        frame.setTitle(username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(625, 450);

        // Create a panel for the checkbox and add it to the frame's content pane
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(encryptCheckbox);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        // Add chat area and message input components
        frame.add(chatPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void sendActionListener(ActionListener event) {
        sendButton.addActionListener(event);
        messageField.addActionListener(event);
    }

    public void updateChatArea(String messageText) {
        updateChatArea(messageText, Color.WHITE);
    }

    public void updateChatArea(String messageText, Color color) {
        chatPanel.updateChatArea(messageText, color);
    }

    public void displayCommand(String text) {
        chatPanel.updateChatArea("\n" + text + "\n");
    }

    public String getMessageInput() {
        return messageField.getText();
    }

    public boolean encryptCheckboxSelected() {
        return encryptCheckbox.isSelected();
    }

    public void setEncryptCheckboxValue(boolean value) {
        encryptCheckbox.setSelected(value);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void clearMessageInput() {
        messageField.setText("");
    }

    public void closeWindow() {
        System.out.println("Closing the application");
        isClosed = true;
        frame.dispose();
        System.exit(StatusCode.OK.getCode());
    }

    public void showError(String text) {
        JOptionPane.showMessageDialog(frame, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean isActive() {
        return !isClosed;
    }

}

