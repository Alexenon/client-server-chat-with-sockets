package chat.handlers.input.message;

import chat.handlers.input.InputHandler;
import chat.models.Message;
import chat.models.User;

public record MessageInputHandler(User author) implements InputHandler {

    @Override
    public Object convertIntoObject(String input) {
        return getMessageFromTextField(input);
    }

    private Message getMessageFromTextField(String input) {
        return isPrivateMessage(input)
                ? getPrivateMessage(input)
                : getPublicMessage(input);
    }

    private boolean isPrivateMessage(String input) {
        return input.contains(":");
    }

    private Message getPrivateMessage(String input) {
        String[] splits = input.split(":");
        String receiverUsername = splits[0];
        User receiver = new User(receiverUsername);
        String messageText = splits[1];

        return new Message(messageText.trim(), author, receiver);
    }

    private Message getPublicMessage(String input) {
        return new Message(input.trim(), author, null);
    }

}
