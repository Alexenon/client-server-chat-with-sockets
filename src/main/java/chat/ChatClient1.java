package chat;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient1 {
    private final JFrame frame;
    private final JTextArea chatArea;
    private final JTextField messageField;
    private PrintWriter output;

    public ChatClient1() {
        frame = new JFrame("Chat Client 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        String username = JOptionPane.showInputDialog(frame, "Enter your username:");
        if (username == null || username.trim().isEmpty()) {
            System.exit(0); // Exit if username is not provided
        }

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
            output = new PrintWriter(socket.getOutputStream(), true);
            // Send the username to the server
            output.println(username);
            new Thread(new IncomingMessageHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient1::new);
    }

    private void sendMessage() {
        String message = messageField.getText();
        output.println(message);
        messageField.setText("");
    }

    private void updateChatArea(String message) {
        SwingUtilities.invokeLater(() -> chatArea.append(message + "\n"));
    }

    public void closeWindow() {
        frame.dispose();
    }

    private class IncomingMessageHandler implements Runnable {
        private final BufferedReader input;

        public IncomingMessageHandler(Socket socket) throws IOException {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                while (true) {
                    String message = input.readLine();
                    if (message == null) {
                        break;
                    }
                    updateChatArea(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                closeWindow();
            }
        }
    }
}
