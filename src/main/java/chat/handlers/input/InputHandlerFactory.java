package chat.handlers.input;

import chat.handlers.input.message.EncryptedMessageInputHandler;
import chat.handlers.input.message.MessageInputHandler;
import chat.models.User;

import javax.crypto.SecretKey;

public record InputHandlerFactory(User user, SecretKey secretKey) {

    // TODO: Add Command Handlers

    public InputHandler getInputHandler(String input, boolean shouldBeEncrypted) {
        return shouldBeEncrypted
                ? new EncryptedMessageInputHandler(input, user, secretKey)
                : new MessageInputHandler(user);
    }

}
