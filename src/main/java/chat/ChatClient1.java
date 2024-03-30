package chat;

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

public class ChatClient1 {
    private final ChatLayout chatLayout;
    private final InputSendingHandler inputSendingHandler;
    private final ResponseHandlerFactory responseHandlerFactory;
    private User user;
    private SecretKey secretKey;
    private ObjectOutputStream outputStream;

    public ChatClient1() {
        chatLayout = new ChatLayout();
        initializeForm();
        connectToTheServer();
        responseHandlerFactory = new ResponseHandlerFactory(user, secretKey);
        inputSendingHandler = new InputSendingHandler(chatLayout, outputStream, user, secretKey);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient1::new);
    }

    private void initializeForm() {
        chatLayout.buildForm();
        chatLayout.sendActionListener(e -> inputSendingHandler.handleSendingMessages());
    }

    private void connectToTheServer() {
        try {
            Socket socket = new Socket("localhost", 8080);
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            user = new User(chatLayout.getUsername());
            inputSendingHandler.sendToServer(user.getUsername());
            new Thread(new IncomingMessageHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSecretKey(SecretKey secretKey) {
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
                        System.out.println("Received secret key from server: " + secretKey);
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
