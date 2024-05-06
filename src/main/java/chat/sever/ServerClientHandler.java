package chat.sever;

import chat.client.models.User;
import chat.utils.StatusCode;
import chat.utils.errors.ServerError;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

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
            initialize();
            // Read messages from client and send them to all clients
            while (!socket.isClosed()) {
                Object receivedObject = inputStream.readObject();
                ServerResponseHandler serverResponseHandler = new ServerResponseHandler(user);
                serverResponseHandler.handleResponse(receivedObject);
            }
        } catch (IOException | ClassNotFoundException e) {
            ServerManager.removeClient(this);
            ServerManager.broadcast("User " + user.getUsername() + " left the chat");
        } finally {
            closeConnection();
        }
    }

    private void initialize() throws IOException, ClassNotFoundException {
        System.out.println("Welcome to the server!");
        initiliazeUserCreation();
        ServerManager.broadcast("User " + user.getUsername() + " joined the chat");
        ServerManager.broadcast("Please, don't spam", user);
    }

    /**
     * When user joins the server from his client side, after setting up his username, he should be forced
     * to send imediately his personal {@link User} instance, so he can be identified and managed correctly by the server
     */
    private void initiliazeUserCreation() throws IOException, ClassNotFoundException {
        User user = (User) inputStream.readObject();

        if (isUsernameTaken(user.getUsername())) {
            String message = "Username '" + user.getUsername() + "' is already taken.";
            ServerError serverError = new ServerError(message, StatusCode.BAD_REQUEST);
            sendObject(serverError);
        }

        this.user = Objects.requireNonNull(user);
    }

    private boolean isUsernameTaken(String username) {
        return ServerManager.getUsers()
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(existingUser -> existingUser.getUsername().equals(username));
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