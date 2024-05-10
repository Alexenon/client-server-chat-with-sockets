package chat.client.ui;

public class TestMe {

    public static void main(String[] args) {
        Console console = new Console();
        console.getFrame().setVisible(true);

        console.sendMessage("Hey");
    }
}
