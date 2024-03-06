package chat.sever;

import chat.models.Message;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * One public key - Multiple private keys <br>
 * When new client connects, the public key is regenerated, together with all private keys
 * */
public class ServerManager {
    private static final SecretKey groupKey = initiateGroupKey();
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        clientHandler.sendMessage(groupKey); // TODO: Think how to send group key
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
            client.sendMessage(message);
        }
    }

    public static synchronized void broadcastMessage(Object object) {
        for (ClientHandler client : clients) {
            System.out.println(object);
            client.sendMessage(object);
        }
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
}
