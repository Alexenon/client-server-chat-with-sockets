package chat.handlers.input;

import chat.models.Message;
import chat.models.User;

public class EncryptedMessageInputHandler implements InputHandler {

    private final String input;
    private final User author;

    public EncryptedMessageInputHandler(String input, User author) {
        this.input = input;
        this.author = author;
    }

    @Override
    public Object convertIntoObject(String input) {
        return getMessageFromTextField();
    }

    private Message getMessageFromTextField() {
        return isPrivateMessage()
                ? getPrivateMessage()
                : getPublicMessage();
    }

    private boolean isPrivateMessage() {
        return input.contains(":");
    }

    private Message getPrivateMessage() {
        String[] splits = input.split(":");
        String receiverUsername = splits[0];
        User receiver = new User(receiverUsername);
        String messageText = splits[1];

        return new Message(messageText.trim(), author, receiver);
    }

    private Message getPublicMessage() {
        return new Message(input.trim(), author, null);
    }

}
