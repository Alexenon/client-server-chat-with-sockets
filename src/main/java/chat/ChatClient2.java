package chat;

import chat.utils.Message;
import chat.utils.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient2 {
    private final User user;
    private final JFrame frame;
    private final JTextArea chatArea;
    private final JTextField messageField;
    private ObjectOutputStream outputStream;

    public ChatClient2() {
        frame = new JFrame("Chat Client 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        String username = JOptionPane.showInputDialog(frame, "Enter your username:");
        if (username == null || username.trim().isEmpty()) {
            System.exit(0); // Exit if username is not provided
        }

        user = new User(username);

        frame.setTitle("Chat Client 1 - " + username);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        messageField = new JTextField();
        messageField.addActionListener(e -> sendMessage());

        bottomPanel.add(messageField, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        bottomPanel.add(sendButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        try {
            Socket socket = new Socket("localhost", 8080);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            // Send the username to the server
            outputStream.writeObject(username);
            outputStream.flush();
            new Thread(new IncomingMessageHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient1::new);
    }

    private void sendMessage() {
        try {
            String messageText = messageField.getText();
            Message message = new Message(messageText, user, null); // TODO: Check for private messages
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        messageField.setText("");
    }

    private void updateChatArea(String message) {
        SwingUtilities.invokeLater(() -> chatArea.append(message + "\n"));
    }

    public void closeWindow() {
        frame.dispose();
    }

    private class IncomingMessageHandler implements Runnable {
        private final ObjectInputStream inputStream;

        public IncomingMessageHandler(Socket socket) throws IOException {
            inputStream = new ObjectInputStream(socket.getInputStream());
        }

        public void run() {
            try {
                while (true) {
                    Message message = (Message) inputStream.readObject();

                    if (message == null) {
                        break;
                    }

                    String messageText = message.getText();
                    System.out.println(messageText);
                    updateChatArea(messageText);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                closeWindow();
            }
        }
    }
}
