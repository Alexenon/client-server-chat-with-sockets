package chat.sever;

import chat.models.Message;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * One public key - Multiple private keys <br>
 * When new client connects, the public key is regenerated, together with all private keys
 * */
public class ServerManager {
    private final PublicKey publicKey;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public ServerManager() {

        // Get the public key from the key pair
        publicKey = generateKeyPar().getPublic();
    }


    public static synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public static synchronized void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public static synchronized void broadcastMessage(String messageText) {
        Message message = new Message(messageText, null, null);
        broadcastMessage(message);
    }

    public static synchronized void broadcastMessage(Message message) {
        for (ClientHandler client : clients) {
            try {
                System.out.println(message);
                client.sendMessage(message);
            } catch (IOException e) {
                System.out.println("Error broadcasting message to client");
            }
        }
    }


    public static synchronized void broadcastMessage(Object object) {
        for (ClientHandler client : clients) {
            try {
                System.out.println(object);
                client.sendMessage(object);
            } catch (IOException e) {
                System.out.println("Error broadcasting message to client");
            }
        }
    }

    private KeyPairGenerator getKeyPairGenerator() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private KeyPair generateKeyPar() {
        return Objects.requireNonNull(getKeyPairGenerator()).generateKeyPair();
    }

    private PrivateKey generatePrivateKey() {
        return generateKeyPar().getPrivate();
    }
}
