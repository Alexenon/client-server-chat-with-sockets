package chat.client.handlers.input;

import chat.client.handlers.input.convertors.InputConvertor;
import chat.client.models.User;
import chat.client.models.commands.Command;
import chat.client.ui.ChatLayout;
import chat.utils.errors.InternalError;

import javax.crypto.SecretKey;
import java.awt.*;
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
        this.secretKey = secretKey;
        this.inputConvertor = new InputConvertor(chatLayout, user, secretKey, this);
    }

    public void handleSendingOperation() {
        String input = chatLayout.getMessageInput();
        boolean shouldBeEncrypted = chatLayout.encryptCheckboxSelected();

        if (input == null || input.isBlank()) {
            return;
        }

        handleSendingObject(input, shouldBeEncrypted);
    }

    public void handleSendingObject(String input, boolean shouldBeEncrypted) {
        Object object = inputConvertor.convertIntoObject(input, shouldBeEncrypted);

        if (object instanceof final Command command) {
            command.execute();
        } else if (object instanceof InternalError internalError) {
            displayError(internalError);
        } else {
            sendToServer(object);
        }

        chatLayout.clearMessageInput();
    }

    public void displayError(InternalError internalError) {
        System.err.println("ERROR" + internalError);
        chatLayout.updateChatArea(internalError.getErrorMessage() + "\n", Color.RED);
    }

    public void sendToServer(Object o) {
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
        this.inputConvertor.setSecretKey(secretKey);
    }
}

