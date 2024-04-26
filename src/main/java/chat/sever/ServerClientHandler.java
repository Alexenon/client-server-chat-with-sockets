package chat.sever;

import chat.client.models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Server's client handler for handling messages/commands from clients using GUI
 */
public class ServerClientHandler extends Thread {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private User user;

    public ServerClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            System.out.println("Welcome to the server!");

            System.out.print("Please enter your username: ");
            String username = (String) inputStream.readObject();
            this.user = new User(username);

            ServerManager.broadcastMessage("User " + user.getUsername() + " joined the chat");
            ServerManager.broadcastMessage("Please, don't spam", user);

            // Read messages from client and send them to all clients
            while (!socket.isClosed()) {
                Object receivedObject = inputStream.readObject();
                ServerResponseHandler serverResponseHandler = new ServerResponseHandler(user);
                serverResponseHandler.handleResponse(receivedObject);
            }
        } catch (IOException | ClassNotFoundException e) {
            ServerManager.removeClient(this);
            ServerManager.broadcastMessage("User " + user.getUsername() + " left the chat");
        } finally {
            closeConnection();
        }
    }

    public void sendObject(Object o) {
        try {
            outputStream.writeObject(o);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }
}