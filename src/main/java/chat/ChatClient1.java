package chat;

import chat.handlers.input.MessageInputHandler;
import chat.handlers.response.ResponseHandler;
import chat.handlers.response.ResponseHandlerFactory;
import chat.models.Message;
import chat.models.User;
import chat.ui.ChatLayout;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient1 {
    private final User user;
    private final ChatLayout chatLayout;
    private final ResponseHandlerFactory responseHandlerFactory;
    private ObjectOutputStream outputStream;

    public ChatClient1() {
        chatLayout = new ChatLayout();
        user = new User(chatLayout.getUsername());
        responseHandlerFactory = new ResponseHandlerFactory(user);
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

    private Message getMessageFromTextField() {
        String messageInput = chatLayout.getMessageInput();
        MessageInputHandler messageInputHandler = new MessageInputHandler(user);
        return (Message) messageInputHandler.convertIntoObject(messageInput);
    }

    private void handleSendingMessage() {
        Message message = getMessageFromTextField();
        sendToServer(message);
    }

    private void sendToServer(Object o) {
        try {
            outputStream.writeObject(o);
            outputStream.flush();
            chatLayout.clearMessageInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class IncomingMessageHandler implements Runnable {
        private final ObjectInputStream inputStream;

        public IncomingMessageHandler(Socket socket) throws IOException {
            inputStream = new ObjectInputStream(socket.getInputStream());
        }

        public void run() {
            try {
                while (true) {
                    Object object = inputStream.readObject();
                    ResponseHandler responseHandler = responseHandlerFactory.createResponseHandler(object);
                    String textToBeDisplayed = responseHandler.handleResult();

                    chatLayout.updateChatArea(textToBeDisplayed);
                    System.out.println(textToBeDisplayed);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                chatLayout.closeWindow();
            }
        }
    }
}
