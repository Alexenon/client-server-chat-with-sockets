package chat;

import chat.models.commands.Command;
import chat.models.errors.InternalError;
import chat.ui.ChatLayout;

import java.io.IOException;
import java.io.ObjectOutputStream;

interface SenderListener {
    boolean isAllowedToBeSent();

    void send(Object o);

    void deny(Object o);
}

public class InputSendHandler {

    ObjectOutputStream outputStream;
    ChatLayout chatLayout;

    public void handle(Object o) {
        if (o instanceof final Command command) {
            displayErrorMessage(command.getResult());
        } else if (o instanceof InternalError internalError) {
            displayErrorMessage(internalError.getErrorMessage());
        } else {
            sendToServer(o);
        }

        chatLayout.clearMessageInput();
    }

    public void displayErrorMessage(String message) {
        System.err.println("ERROR" + message);
        chatLayout.updateChatArea(message);
    }

    public SenderListener getSenderListener(Object o) {
        if (o instanceof Command) {
            return new LocalCommandSenderListener();
        } else if (o instanceof InternalError) {
            return new ErrorSenderListener();
        }
        return new DefaultSenderListener();
    }

    private void sendToServer(Object o) {
        if (o == null) return;

        try {
            System.out.println("Sending object: " + o);
            outputStream.writeObject(o);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ErrorSenderListener implements SenderListener {
    @Override
    public boolean isAllowedToBeSent() {
        return false;
    }

    @Override
    public void send(Object o) {
        throw new IllegalCallerException();
    }

    @Override
    public void deny(Object o) {
        InternalError internalError = (InternalError) o;
        System.out.println(internalError.getErrorMessage());
    }
}

class LocalCommandSenderListener implements SenderListener {
    @Override
    public boolean isAllowedToBeSent() {
        return false;
    }

    @Override
    public void send(Object o) {
        throw new IllegalCallerException();
    }

    @Override
    public void deny(Object o) {
        Command command = (Command) o;
        System.out.println(command.getErrorMessage());
    }
}


class DefaultSenderListener implements SenderListener {
    @Override
    public boolean isAllowedToBeSent() {
        return true;
    }

    @Override
    public void send(Object o) {
        System.out.println("Sending object");
    }

    @Override
    public void deny(Object o) {
        throw new IllegalCallerException();
    }
}

