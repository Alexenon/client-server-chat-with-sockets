package chat.sever;

import chat.models.Message;
import chat.models.User;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

// TODO: Think how to send group key
// TODO: Implement sending encrypted key, instead of the real one directly
// TODO: Reset key, after a new user join or leave the channel

public class ServerManager {
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static SecretKey groupKey = initiateGroupKey();

    public static synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        clientHandler.sendObject(groupKey);
    }

    public static synchronized void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public static synchronized void broadcastMessage(String messageText) {
        Message message = new Message(messageText, null, null);
        broadcastMessage(message);
        System.out.println(message);
    }

    public static synchronized void broadcastMessage(Message message) {
        for (ClientHandler client : clients) {
            System.out.println(message);
            client.sendObject(message);
        }
    }

    public static synchronized void broadcastMessage(Object object) {
        for (ClientHandler client : clients) {
            System.out.println(object);
            client.sendObject(object);
        }
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
        groupKey = initiateGroupKey();
    }

}
