package chat;

import chat.model.Message;

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
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                // Read username from client
                // When a user join the server, and enters the username, the username is sent to the server
                String username = (String) inputStream.readObject();
                System.out.println("User connected: " + username);

                // Broadcast message to all clients
                broadcastMessage(new Message("User " + username + " joined the chat", null, null));

                // Read messages from client
                while (!serverSocket.isClosed()) {
                    Message message = (Message) inputStream.readObject();
                    broadcastMessage(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client left the chat");
//                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcastMessage(Message message) throws IOException {
            for (ClientHandler client : clients) {
                try {
                    client.outputStream.writeObject(message);
                    client.outputStream.flush();
                    System.out.println(message);
                } catch (IOException e) {
                    System.out.println("Client discounted");
//                    e.printStackTrace();
                    inputStream.close();
                    outputStream.close();
                }
            }
        }
    }
}

