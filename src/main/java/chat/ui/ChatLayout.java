package chat.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatLayout {
    private final JFrame frame = new JFrame();
    private final JTextArea chatArea = new JTextArea();
    private final JTextField messageField = new JTextField();
    private final JCheckBox encryptCheckbox = new JCheckBox("Encrypt");
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

        // Create a panel for the checkbox and add it to the frame's content pane
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(encryptCheckbox);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        // Set layout for chat panel
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        frame.getContentPane().add(chatPanel, BorderLayout.CENTER);

        // Add chat panel to a scroll pane and add it to the frame's content pane
        JScrollPane scrollPane = new JScrollPane(chatPanel);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add chat area and message input components
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

    public boolean encryptCheckboxSelected() {
        return encryptCheckbox.isSelected();
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



    ///////////////////

    private final JPanel chatPanel = new JPanel();

    public void addMessage(String message) {
        boolean isSender = true;
        JPanel messagePanel = createMessagePanel(message, isSender);
        chatPanel.add(messagePanel);
        chatPanel.revalidate(); // Refresh layout
        chatPanel.repaint(); // Repaint chat panel
    }

    private JPanel createMessagePanel(String message, boolean isSender) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(message);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(messageLabel, BorderLayout.CENTER);

        if (isSender) {
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(e -> {
                chatPanel.remove(panel);
                chatPanel.revalidate();
                chatPanel.repaint();
            });
            panel.add(deleteButton, BorderLayout.EAST);
        }

        return panel;
    }

    private void sendMessage() {
        String message = messageField.getText();
        // For demonstration purposes, let's assume the user is the sender
        addMessage(message);
        messageField.setText(""); // Clear the message field after sending
    }

}

