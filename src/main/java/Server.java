import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private final int port;
    private final Scanner scanner = new Scanner(System.in);

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
        server.start();
    }

    public void start() {
        try {
            System.out.println("Starting server...");
            serverSocket = new ServerSocket(port);
            System.out.println("Server started successfully");
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            initializeSender();
            initializeReceiver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeSender() {
        Thread sender = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                while (true) {
                    msg = scanner.nextLine();
                    out.println(msg);
                    out.flush();
                }
            }
        });
        sender.start();
    }

    private void initializeReceiver() {
        Thread receiver = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                try {
                    msg = in.readLine();
                    while (msg != null) {
                        System.out.println("Client : " + msg);
                        msg = in.readLine();

                        if (msg.equals("EXIT"))
                            break;
                    }

                    System.out.println("Client disconnected");

                    out.close();
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receiver.start();
    }

}