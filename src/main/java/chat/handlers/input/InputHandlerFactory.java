package chat.handlers.input;

import chat.handlers.input.message.EncryptedMessageInputHandler;
import chat.handlers.input.message.MessageInputHandler;
import chat.models.User;

public record InputHandlerFactory(User user) {

    // TODO: Add Command Handlers

    public InputHandler getInputHandler(String input, boolean shouldBeEncrypted) {
        return shouldBeEncrypted
                ? new EncryptedMessageInputHandler(input, user)
                : new MessageInputHandler(user);
    }

}
