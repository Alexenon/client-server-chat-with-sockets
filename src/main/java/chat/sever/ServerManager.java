package chat.sever;

import chat.models.Message;
import chat.models.User;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static final List<ServerClientHandler> clients = new ArrayList<>();
    private static SecretKey secretKey = initiateGroupKey();

    public static synchronized void addClient(ServerClientHandler serverClientHandler) {
        clients.add(serverClientHandler);
        serverClientHandler.sendObject(secretKey); // TODO: Think how to send group key
    }

    public static synchronized void removeClient(ServerClientHandler serverClientHandler) {
        clients.remove(serverClientHandler);
    }

    public static synchronized void broadcastMessage(String messageText) {
        Message message = new Message(messageText);
        broadcastMessage(message);
    }

    public static synchronized void broadcastMessage(Object object) {
        for (ServerClientHandler client : clients) {
            System.out.println(object);
            client.sendObject(object);
        }
    }

    public static synchronized void broadcastMessage(String messageText, User receiver) {
        Message message = new Message(messageText);
        broadcastMessage(message, receiver);
    }

    public static synchronized void broadcastMessage(Object object, User receiver) {
        ServerClientHandler serverClientHandlerReceiver = clients.stream()
                .filter(cl -> cl.getUser() == receiver)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find the receiver user " + receiver));

        serverClientHandlerReceiver.sendObject(object);
        System.out.println(object);
    }

    public static SecretKey initiateGroupKey() {
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
