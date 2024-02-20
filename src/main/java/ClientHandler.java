import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final BufferedReader input;
    private final PrintStream output;
    private String name;

    public ClientHandler(Socket client) {
        this.input = new BufferedReader(new InputStreamReader(System.in));
        this.output = new PrintStream(System.out);
        setClientName();
        start();
    }

    public void setClientName() {
        try {
            this.name = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return name;
    }

    public void sendMessage(String username, String msg) {
        output.println(username + ": " + msg);
    }

    public void run() {
        String line;
        try {
            while (true) {
                line = input.readLine();
                // TODO: Should send to the server
                output.println(line);
                output.flush();
//                if (line.equals("end")) {
//                    clients.remove(this);
//                    users.remove(name);
//                    break;
//                }
//                broadcast(name, line); // method  of outer class - send messages to all
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
