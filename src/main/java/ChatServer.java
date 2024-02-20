import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    // TODO: Add message that user connected to the server
    // TODO: Add message that user disconnected from the server

    private static final int SERVER_PORT = 9999;
    List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ChatServer chatServer = new ChatServer();
        chatServer.process();
    }

    public void process() throws Exception {
        System.out.println("Starting server on port " + SERVER_PORT + "...");
        ServerSocket server = new ServerSocket(SERVER_PORT);
        System.out.println("Server started successfully");
        while (true) {
            Socket client = server.accept();
            ClientHandler c = new ClientHandler(client);
            clients.add(c);
            System.out.println("User " + c.getClientName() + " connected to the server.");  // FIXME: NOT WORKING !!!
        }
    }

    public void broadcast(String user, String message) {
        for (ClientHandler c : clients)
            c.sendMessage(user, message);
    }

}
