package chat.handlers.input;

import chat.handlers.input.message.EncryptedMessageInputHandler;
import chat.handlers.input.message.MessageInputHandler;
import chat.models.User;

import java.security.PublicKey;

public record InputHandlerFactory(User user, PublicKey publicKey) {

    // TODO: Add Command Handlers

    public InputHandler getInputHandler(String input, boolean shouldBeEncrypted) {
        return shouldBeEncrypted
                ? new EncryptedMessageInputHandler(input, user, publicKey)
                : new MessageInputHandler(user);
    }

}
