package chat;

import chat.handlers.response.ResponseHandler;
import chat.handlers.response.ResponseHandlerFactory;
import chat.models.User;
import chat.models.temp.InputConvertor;
import chat.ui.ChatLayout;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient1 {
    private final User user;
    private final ChatLayout chatLayout;
    private SecretKey secretKey;
    private ObjectOutputStream outputStream;
    private ResponseHandlerFactory responseHandlerFactory;

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
        Object objectToBeSent = getObjectToSend();
        sendToServer(objectToBeSent);
    }

    private void sendToServer(Object o) {
        if (o == null) return;

        System.out.println("Sending object: " + o);

        try {
            outputStream.writeObject(o);
            outputStream.flush();
            chatLayout.clearMessageInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object getObjectToSend() {
        String input = chatLayout.getMessageInput();
        boolean shouldBeEncrypted = chatLayout.encryptCheckboxSelected();

        InputConvertor inputConvertor = new InputConvertor(user, secretKey, shouldBeEncrypted);
        return inputConvertor.convertIntoObject(input);
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
                        ChatClient1.this.secretKey = secretKey;
                        responseHandlerFactory = new ResponseHandlerFactory(user, secretKey);
                        System.out.println("Received secret key from server: " + secretKey);
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
