package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient2 {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private PrintWriter out;

    public ChatClient2() {
        frame = new JFrame("Chat Client 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        bottomPanel.add(messageField, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        bottomPanel.add(sendButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        try {
            Socket socket = new Socket("localhost", 8080);
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new IncomingMessageHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        out.println(message);
        messageField.setText("");
    }

    private class IncomingMessageHandler implements Runnable {
        private BufferedReader in;

        public IncomingMessageHandler(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                while (true) {
                    String message = in.readLine();
                    if (message == null) {
                        break;
                    }
                    updateChatArea(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateChatArea(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatClient2();
            }
        });
    }
}
