package chat.sever;

import chat.model.Message;
import chat.model.User;

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

            // Read messages from client
            while (!socket.isClosed()) {
                Message message = (Message) inputStream.readObject();
                ServerManager.broadcastMessage(message);
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

    public void sendMessage(Message message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

}