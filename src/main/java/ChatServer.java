import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static final int SERVER_PORT = 9999;
    List<ClientHandler> clients = new ArrayList<>();

    public static void main(String... args) throws Exception {
        ChatServer chatServer = new ChatServer();
        chatServer.process();
    }

    public void process() throws Exception {
        ServerSocket server = new ServerSocket(SERVER_PORT);
        System.out.println("Server Started...");
        while (true) {
            Socket client = server.accept();
            ClientHandler c = new ClientHandler(client);
            clients.add(c);
        }
    }

    public void broadcast(String user, String message) {
        for (ClientHandler c : clients)
            c.sendMessage(user, message);
    }

}
