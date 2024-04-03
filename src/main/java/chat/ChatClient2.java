package chat;

import chat.handlers.input.InputSendingHandler;
import chat.handlers.response.ResponseHandler;
import chat.handlers.response.ResponseHandlerFactory;
import chat.models.User;
import chat.ui.ChatLayout;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient2 {
    private final User user;
    private final ChatLayout chatLayout;
    private final InputSendingHandler inputSendingHandler;
    private final ResponseHandlerFactory responseHandlerFactory;
    private SecretKey secretKey;
    private ObjectOutputStream outputStream;

    public ChatClient2() {
        chatLayout = new ChatLayout();
        user = new User(chatLayout.getUsername());
        initializeConnection();
        responseHandlerFactory = new ResponseHandlerFactory(user, secretKey);
        inputSendingHandler = new InputSendingHandler(chatLayout, outputStream, user, secretKey);
        setupConnection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient2::new);
    }

    private void initializeConnection() {
        try {
            Socket socket = new Socket("localhost", 8080);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            IncomingMessageHandler incomingMessageHandler = new IncomingMessageHandler(socket);
            Thread thread = new Thread(incomingMessageHandler);
            thread.start();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't connect to the server. " + e.getLocalizedMessage());
        }
    }

    private void setupConnection() {
        chatLayout.sendActionListener(e -> inputSendingHandler.handleSendingMessages());
        inputSendingHandler.sendToServer(user.getUsername());
    }

    public void updateSecretKey(SecretKey secretKey) {
        System.out.println("Updating secret key: " + secretKey);
        this.secretKey = secretKey;
        this.inputSendingHandler.setSecretKey(secretKey);
        this.responseHandlerFactory.setSecretKey(secretKey);
    }

    private class IncomingMessageHandler implements Runnable {
        private final ObjectInputStream inputStream;

        public IncomingMessageHandler(Socket socket) throws IOException {
            inputStream = new ObjectInputStream(socket.getInputStream());
        }

        public void run() {
            try {
                while (chatLayout.isActive()) {
                    Object object = inputStream.readObject();

                    System.out.println("Received object: " + object);

                    if (object instanceof SecretKey secretKey) {
                        updateSecretKey(secretKey);
                        continue;
                    }

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
