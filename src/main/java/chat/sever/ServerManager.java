package chat.sever;

import chat.models.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static final List<ClientHandler> clients = new ArrayList<>();

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

}
