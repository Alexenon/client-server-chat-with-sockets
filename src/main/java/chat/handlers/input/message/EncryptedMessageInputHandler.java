package chat.handlers.input.message;

import chat.handlers.input.InputHandler;
import chat.models.EncryptedMessage;
import chat.models.User;

public record EncryptedMessageInputHandler(String input, User author) implements InputHandler {

    @Override
    public Object convertIntoObject(String input) {
        return getMessageFromTextField();
    }

    private EncryptedMessage getMessageFromTextField() {
        return isPrivateMessage()
                ? getPrivateMessage()
                : getPublicMessage();
    }

    private boolean isPrivateMessage() {
        return input.contains(":");
    }

    private EncryptedMessage getPrivateMessage() {
        String[] splits = input.split(":");
        String receiverUsername = splits[0];
        User receiver = new User(receiverUsername);
        String messageText = splits[1];

        return new EncryptedMessage(messageText.trim(), author, receiver);
    }

    private EncryptedMessage getPublicMessage() {
        return new EncryptedMessage(input.trim(), author, null);
    }

}
