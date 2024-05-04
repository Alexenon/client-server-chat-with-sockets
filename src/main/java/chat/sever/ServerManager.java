package chat.sever;

import chat.EncryptUtils;
import chat.client.models.Message;
import chat.client.models.User;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static SecretKey secretKey = EncryptUtils.generateSecretKey();
    private static final List<ServerClientHandler> clients = new ArrayList<>();

    public static synchronized void addClient(ServerClientHandler serverClientHandler) {
        clients.add(serverClientHandler);
        resetSecurityKey();
        broadcast(secretKey); // TODO: Think how to send group key
    }

    public static synchronized void removeClient(ServerClientHandler serverClientHandler) {
        clients.remove(serverClientHandler);
        resetSecurityKey();
        broadcast(secretKey);
    }

    /**
     * Resets the current secret key with a new one, when a new user joins or leave the chat
     * for more privacy between chat members
     * */
    public static void resetSecurityKey() {
        secretKey = EncryptUtils.generateSecretKey();
    }

    public static synchronized void broadcast(String messageText) {
        Message message = new Message(messageText);
        broadcast(message);
    }

    public static synchronized void broadcast(Object object) {
        for (ServerClientHandler client : clients) {
            System.out.println(object);
            client.sendObject(object);
        }
    }

    public static synchronized void broadcast(String messageText, User receiver) {
        Message message = new Message(messageText);
        broadcast(message, receiver);
    }

    public static synchronized void broadcast(Object object, User receiver) {
        ServerClientHandler serverClientHandlerReceiver = clients.stream()
                .filter(cl -> cl.getUser() == receiver)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find the receiver user " + receiver));

        serverClientHandlerReceiver.sendObject(object);
        System.out.println(object);
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }

    public static List<User> getUsers() {
        return clients.stream().map(ServerClientHandler::getUser).toList();
    }
}
