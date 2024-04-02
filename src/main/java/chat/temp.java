//package chat;
//
//import chat.handlers.response.ResponseHandler;
//import chat.handlers.response.ResponseHandlerFactory;
//import chat.models.User;
//import chat.ui.ChatLayout;
//
//import javax.crypto.SecretKey;
//import javax.swing.*;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//
//public class ChatClient1 {
//    private final Socket socket;
//    private final ChatLayout chatLayout;
//    private final ObjectInputStream objectInputStream;
//    private final ObjectOutputStream objectOutputStream;
//    private final InputSendingHandler inputSendingHandler;
//    private final ResponseHandlerFactory responseHandlerFactory;
//    private final IncomingMessageHandler incomingMessageHandler;
//    private User user;
//    private SecretKey secretKey;
//
//    public ChatClient1() {
//        try {
//            chatLayout = new ChatLayout();
//            initialize();
//            socket = new Socket("localhost", 8080);
//            objectInputStream = new ObjectInputStream(socket.getInputStream());
//            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//            incomingMessageHandler = new IncomingMessageHandler(objectInputStream);
//            inputSendingHandler = new InputSendingHandler(chatLayout, objectOutputStream, user, secretKey);
//            responseHandlerFactory = new ResponseHandlerFactory(user, secretKey);
//            connectToTheServer();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(ChatClient1::new);
//    }
//
//    private void initialize() {
//        chatLayout.buildForm();
//        user = new User(chatLayout.getUsername());
//    }
//
//    private void connectToTheServer() {
//        chatLayout.sendActionListener(e -> inputSendingHandler.handleSendingMessages());
//        inputSendingHandler.sendToServer(user.getUsername());
//        new Thread(incomingMessageHandler).start();
//    }
//
//    public void updateSecretKey(SecretKey secretKey) {
//        this.secretKey = secretKey;
//        this.inputSendingHandler.setSecretKey(secretKey);
//        this.responseHandlerFactory.setSecretKey(secretKey);
//    }
//
//    private class IncomingMessageHandler implements Runnable {
//        private final ObjectInputStream inputStream;
//
//        public IncomingMessageHandler(ObjectInputStream inputStream) {
//            this.inputStream = inputStream;
//        }
//
//        public void run() {
//            try {
//                while (chatLayout.isActive()) {
//                    Object object = inputStream.readObject();
//
//                    System.out.println("Received object: " + object);
//
//                    if (object instanceof SecretKey secretKey) {
//                        System.out.println("Received secret key from server: " + secretKey);
//                        updateSecretKey(secretKey);
//                        continue;
//                    }
//
//                    ResponseHandler responseHandler = responseHandlerFactory.createResponseHandler(object);
//                    String textToBeDisplayed = responseHandler.handleResult();
//
//                    chatLayout.updateChatArea(textToBeDisplayed);
//                    System.out.println(textToBeDisplayed);
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//                chatLayout.closeWindow();
//            }
//        }
//    }
//}
