package chat;

import chat.model.Message;
import chat.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// TODO:
//  - encryption
//  - commands
//  - handle same username situations
//  - bytes to different objects (message, error, user, etc.)

public class ChatServer {
    private static final int PORT = 8080;
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
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

                welcomeNewUser();

                // Read messages from client
                while (!serverSocket.isClosed()) {
                    Message message = (Message) inputStream.readObject();
                    broadcastMessage(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                String leavingText = "User " + user.getUsername() + " left the chat";
                broadcastMessage(leavingText);
                System.out.println(leavingText);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcastMessage(String messageText) {
            Message message = new Message(messageText, null, null);
            broadcastMessage(message);
        }

        private void broadcastMessage(Message message) {
            for (ClientHandler client : clients) {
                try {
                    client.outputStream.writeObject(message);
                    client.outputStream.flush();
                    System.out.println(message);
                } catch (IOException e) {
                    System.out.println("User " + user.getUsername() + " got disconnected");
                }
            }
        }

        private void welcomeNewUser() {
            String welcomingText = "User " + user.getUsername() + " joined the chat";
            broadcastMessage(welcomingText);
            System.out.println(welcomingText);
        }

    }
}

