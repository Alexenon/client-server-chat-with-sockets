import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    String username;
    PrintWriter pw;
    BufferedReader br;
    Socket client;

    public ChatClient(String username) throws Exception {
        this.username = username;

        String serverName = "localhost";
        int serverPort = 9999;
        System.out.println("Connecting to the server " + serverName + ":" + serverPort + "...");
        client = new Socket(serverName, serverPort);
        System.out.println("Welcome to the server " + username + "!");
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        pw = new PrintWriter(client.getOutputStream(), true);
        pw.println(username);
        new MessagesThread().start();
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        try {
            new ChatClient(name);
        } catch (Exception ex) {
            System.out.println("Error --> " + ex.getMessage());
        }
    }

    // TODO: Shutdown server
//    public void actionPerformed(ActionEvent evt) {
//        if (evt.getSource() == btnExit) {
//            pw.println("end");  // send end to server so that server knows about the termination
//            System.exit(0);
//        } else {
//            // send message to server
//            pw.println(tfInput.getText());
//        }
//    }

    // inner class for Messages Thread
    class MessagesThread extends Thread {
        public void run() {
            String line;
            try {
                while (true) {
                    line = br.readLine();
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
