package chat;

import chat.utils.Message;
import chat.utils.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient1 {
    private final User user;
    private final ChatLayout chatLayout;
    private ObjectOutputStream outputStream;

    public ChatClient1() {
        chatLayout = new ChatLayout();
        user = new User(chatLayout.getUsername());
        initialize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient1::new);
    }

    private void initialize() {
        chatLayout.buildForm();
        chatLayout.sendActionListener(e -> handleSendingMessage());
        connectToTheServer();
    }

    private void connectToTheServer() {
        try {
            Socket socket = new Socket("localhost", 8080);
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            // Send the username to the server
            outputStream.writeObject(chatLayout.getUsername());
            outputStream.flush();
            new Thread(new IncomingMessageHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSendingMessage() {
        try {
            Message message = getMessageFromTextField();
            outputStream.writeObject(message);
            outputStream.flush();
            chatLayout.clearMessageInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message getMessageFromTextField() {
        return isSendingMessagePrivate()
                ? getPrivateMessage()
                : getPublicMessage();
    }

    private boolean isSendingMessagePrivate() {
        return chatLayout.getMessageInput().contains(":");
    }

    private Message getPrivateMessage() {
        String[] split = chatLayout.getMessageInput().split(":");
        String receiverUsername = split[0];
        String messageText = split[1];
        User receiver = new User(receiverUsername);

        return new Message(messageText.trim(), user, receiver);
    }

    private Message getPublicMessage() {
        return new Message(chatLayout.getMessageInput().trim(), user, null);
    }

    private String getMessageTextToBeDisplayed(Message message) {
        User author = message.getAuthor();
        User receiver = message.getReceiver();
        String text = message.getText();

        // Check if author exists and is not the same as receiver
        if (author != null && !author.getUsername().equals(receiver.getUsername())) {
            return author.getUsername() + ": " + text;
        }

        return text;
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

                    if (message == null)
                        break;

                    String messageTextToBeDisplayed = getMessageTextToBeDisplayed(message);
                    chatLayout.updateChatArea(messageTextToBeDisplayed);
                    System.out.println(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                chatLayout.closeWindow();
            }
        }
    }
}
