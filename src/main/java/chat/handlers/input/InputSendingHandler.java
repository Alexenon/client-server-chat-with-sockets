package chat.handlers.input;

import chat.handlers.input.convertors.InputConvertor;
import chat.models.User;
import chat.models.commands.Command;
import chat.models.errors.InternalError;
import chat.ui.ChatLayout;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class InputSendingHandler {
    private final ChatLayout chatLayout;
    private final ObjectOutputStream outputStream;
    private final InputConvertor inputConvertor;
    private User user;
    private SecretKey secretKey;

    public InputSendingHandler(ChatLayout chatLayout, ObjectOutputStream outputStream, User user, SecretKey secretKey) {
        this.chatLayout = chatLayout;
        this.outputStream = outputStream;
        this.inputConvertor = new InputConvertor(user, secretKey);
    }

    public void handleSendingMessages() {
        String input = chatLayout.getMessageInput();
        boolean shouldBeEncrypted = chatLayout.encryptCheckboxSelected();
        Object objectToBeSent = inputConvertor.convertIntoObject(input, shouldBeEncrypted);
        handle(objectToBeSent);
    }

    private void handle(Object o) {
        if (o instanceof final Command command) {
            displaySimpleMessage(command.getResult());
        } else if (o instanceof InternalError internalError) {
            displayErrorMessage(internalError.getErrorMessage());
        } else {
            sendToServer(o);
        }

        chatLayout.clearMessageInput();
    }

    public void displaySimpleMessage(String message) {
        System.err.println(message);
        chatLayout.updateChatArea(message);
    }

    public void displayErrorMessage(String message) {
        System.err.println("ERROR" + message);
        chatLayout.updateChatArea(message);
    }

    public void sendToServer(Object o) {
        if (o == null) return;

        try {
            System.out.println("Sending object: " + o);
            outputStream.writeObject(o);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}

