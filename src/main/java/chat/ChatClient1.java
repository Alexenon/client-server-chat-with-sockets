package chat;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.handlers.response.ResponseHandler;
import chat.client.handlers.response.ResponseHandlerFactory;
import chat.client.models.User;
import chat.client.ui.ChatLayout;
import chat.utils.ServerConfiguration;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class ChatClient1 {
    private final User user;
    private final ChatLayout chatLayout;
    private final InputSendingHandler inputSendingHandler;
    private final ResponseHandlerFactory responseHandlerFactory;
    private SecretKey secretKey;
    private ObjectOutputStream outputStream;

    public ChatClient1() {
        chatLayout = new ChatLayout();
        user = new User(chatLayout.getUsername());
        initializeConnection();
        responseHandlerFactory = new ResponseHandlerFactory(user, secretKey);
        inputSendingHandler = new InputSendingHandler(chatLayout, outputStream, user, secretKey);
        setupConnection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient1::new);
    }

    private void initializeConnection() {
        try {
            Socket socket = new Socket(ServerConfiguration.SERVER_NAME, ServerConfiguration.SERVER_PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            IncomingMessageHandler incomingMessageHandler = new IncomingMessageHandler(socket);
            Thread thread = new Thread(incomingMessageHandler);
            thread.start();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't connect to the server. " + e.getLocalizedMessage());
        }
    }

    private void setupConnection() {
        chatLayout.sendActionListener(e -> inputSendingHandler.handleSendingOperation());
        inputSendingHandler.sendToServer(user.getUsername());
    }

    public void updateSecretKey(SecretKey secretKey) {
        Objects.requireNonNull(secretKey);
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
