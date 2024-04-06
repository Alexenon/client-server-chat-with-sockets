package chat.models.commands.actions;

import chat.ui.ChatLayout;

import java.io.IOException;
import java.net.Socket;

public class ActionInvoker {

    private final Socket socket;
    private final ChatLayout chatLayout;

    public ActionInvoker(Socket socket, ChatLayout chatLayout) {
        this.socket = socket;
        this.chatLayout = chatLayout;
    }

    private void executeAction(Action a) {
        switch (a) {
            case SHOW_HELP -> handleHelp();
            case EXIT -> handleExit();
        }
    }

    private void handleHelp() {
        chatLayout.updateChatArea("");
    }

    private void handleExit() {
        chatLayout.closeWindow();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
