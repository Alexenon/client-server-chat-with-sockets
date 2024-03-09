package chat.sever;

import chat.models.Message;
import chat.models.User;
import chat.utils.CipherManager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

// TODO: Think how to send group key
// TODO: Implement sending encrypted key, instead of the real one directly

public class ServerManager {
    private static final CipherManager cipherManager = CipherManager.getInstance();
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static SecretKey groupKey = initiateGroupKey();

    public static synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        resetSecurityKey();
    }

    public static synchronized void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        resetSecurityKey();
    }

    public static synchronized void broadcastObject(String messageText) {
        Message message = new Message(messageText, null, null);
        broadcastObject(message);
    }

    public static synchronized void broadcastObject(Object object) {
        for (ClientHandler client : clients) {
            client.sendObject(object);
        }
    }

    public static synchronized void broadcastObject(Object object, User receiver) {
        ClientHandler clientHandlerReceiver = clients.stream()
                .filter(cl -> cl.getUser() == receiver)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find the receiver user " + receiver));

        clientHandlerReceiver.sendObject(object);
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

    // Resetting security and sending it to each user
    public static void resetSecurityKey() {
        groupKey = initiateGroupKey();
        clients.forEach(clientHandler -> {
            User user = clientHandler.getUser();
            SecretKey encryptedSecretKey = cipherManager.encryptSecretKey(groupKey, user.getPublicKey());
            broadcastObject(encryptedSecretKey, user);
        });
    }

}
