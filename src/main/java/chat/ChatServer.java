package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO:
//  - chat layout
//  - encryption
//  - private messages
//  - commands

public class ChatServer {
    private static final int PORT = 8080;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
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
        private String username;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                output.println("Successfully connected to the server");
                askUsername();

                broadcastMessage("User " + username + " joined the chat");

                String inputLine;
                while ((inputLine = input.readLine()) != null) {
                    System.out.println("Received from " + username + ": " + inputLine);
                    broadcastMessage(username + ": " + inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void askUsername() throws IOException {
            output.println("Please enter your username:");
            username = input.readLine();
            System.out.println("User connected: " + username);
        }

        private void broadcastMessage(String message) {
//            if (isPrivateMessage(message)) {
//                sendPrivateMessage(message);
//            } else {
//                sendPublicMessage(message);
//            }

            System.out.println("broadcasting: " + message);

            sendPublicMessage(message);
        }

        /**
         * Format: `username: username: Text`
         */
        private void sendPrivateMessage(String message) {
            String[] args = message.split(" ");
            String usernameReceiver = args[0]
                    .replace(":", "")
                    .toLowerCase();

            Optional<ClientHandler> receiver = clients.stream()
                    .filter(client -> client.username.toLowerCase().equals(usernameReceiver))
                    .findFirst();

            String textToBeDisplayed = receiver.isPresent()
                    ? message
                    : "User " + usernameReceiver + " is not found";

            receiver.ifPresent(clientHandler -> clientHandler.output.println(textToBeDisplayed));
            this.output.println(textToBeDisplayed); // Show the message to the sender also
        }

        private boolean isPrivateMessage(String message) {
            return message.split(" ")[0].matches("\\w+:");
        }

        private void sendPublicMessage(String message) {
            for (ClientHandler client : clients) {
                client.output.println(message);
            }
        }
    }
}
