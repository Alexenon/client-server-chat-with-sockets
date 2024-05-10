package chat.client.ui;

import java.awt.*;

public class TestMe {

    public static void main(String[] args) {
        Console console = new Console();
        console.getFrame().setVisible(true);

        console.sendMessage("Error: Missing required packages", Color.RED);
        console.sendMessage("Hey", Color.CYAN);
    }
}
