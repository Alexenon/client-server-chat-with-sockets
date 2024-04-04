package chat;

import chat.sever.ClientHandler;
import chat.sever.ServerManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// TODO:
//  - commands
//  - handle same username situations

public class ChatServer {
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server started on port " + SERVER_PORT);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
                ServerManager.addClient(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

