package chat.sever;

import chat.models.Message;
import chat.models.User;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static SecretKey secretKey = initiateGroupKey();

    public static synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        clientHandler.sendObject(secretKey); // TODO: Think how to send group key
    }

    public static synchronized void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public static synchronized void broadcastMessage(String messageText) {
        Message message = new Message(messageText);
        broadcastMessage(message);
    }

    public static synchronized void broadcastMessage(Object object) {
        for (ClientHandler client : clients) {
            System.out.println(object);
            client.sendObject(object);
        }
    }

    public static synchronized void broadcastMessage(String messageText, User receiver) {
        Message message = new Message(messageText);
        broadcastMessage(message, receiver);
    }

    public static synchronized void broadcastMessage(Object object, User receiver) {
        ClientHandler clientHandlerReceiver = clients.stream()
                .filter(cl -> cl.getUser() == receiver)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find the receiver user " + receiver));

        clientHandlerReceiver.sendObject(object);
        System.out.println(object);
    }

    private static SecretKey initiateGroupKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException");
        }
    }

    public static void resetSecurityKey() {
        secretKey = initiateGroupKey();
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }

}
