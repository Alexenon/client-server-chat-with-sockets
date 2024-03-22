package chat;

import chat.handlers.input.InputHandler;
import chat.handlers.input.MessageInputHandlerFactory;
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
    private MessageInputHandlerFactory messageInputHandlerFactory;
    private ResponseHandlerFactory responseHandlerFactory;
    private ObjectOutputStream outputStream;
    private SecretKey groupKey;

    public ChatClient2() {
        chatLayout = new ChatLayout();
        user = new User(chatLayout.getUsername());
        initialize();
        messageInputHandlerFactory = new MessageInputHandlerFactory(user, groupKey);
        responseHandlerFactory = new ResponseHandlerFactory(user, groupKey);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient2::new);
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

        InputHandler inputHandler = messageInputHandlerFactory.getInputHandler(input, shouldBeEncrypted);
        return inputHandler.convertIntoObject(input);
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

                    if (object instanceof SecretKey secretKey) {
                        groupKey = secretKey;
                        messageInputHandlerFactory = new MessageInputHandlerFactory(user, secretKey);
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
