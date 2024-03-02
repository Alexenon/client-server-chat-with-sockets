package chat.sever;

import chat.models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
* Client Handler works for handling messages from clients using GUI
* */
public class ClientHandler extends Thread {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private User user;

    public ClientHandler(Socket socket) throws IOException {
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

            String welcomingText = "User " + user.getUsername() + " joined the chat";
            ServerManager.broadcastMessage(welcomingText);
            System.out.println(welcomingText);

            // Read messages from client and send them to all clients
            while (!socket.isClosed()) {
                Object messageObject = inputStream.readObject();
                ServerManager.broadcastMessage(messageObject);
            }
        } catch (IOException | ClassNotFoundException e) {
            String leavingText = "User " + user.getUsername() + " left the chat";
            ServerManager.broadcastMessage(leavingText);
            System.out.println(leavingText);
            ServerManager.removeClient(this);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Object o) throws IOException {
        outputStream.writeObject(o);
        outputStream.flush();
    }

}