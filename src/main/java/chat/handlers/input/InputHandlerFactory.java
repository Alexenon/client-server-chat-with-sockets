package chat.handlers.input;

import chat.handlers.input.command.CommandInputHandler;
import chat.handlers.input.message.EncryptedMessageInputHandler;
import chat.handlers.input.message.MessageInputHandler;
import chat.handlers.response.ErrorHandlerImpl;
import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;
import chat.models.commands.Command;

import javax.crypto.SecretKey;

public class InputHandlerFactory {
    private final User user;
    private final SecretKey secretKey;

    public InputHandlerFactory(User user, SecretKey secretKey) {
        this.user = user;
        this.secretKey = secretKey;
    }

//    public InputHandler createInputHandler(Object object) {
//        if (object instanceof Message message) {
//            return new MessageInputHandler(message, user);
//        } else if (object instanceof EncryptedMessage encryptedMessage) {
//            return new EncryptedMessageInputHandler(encryptedMessage, user, secretKey);
//        } else if (object instanceof Command command) {
//            return new CommandInputHandler();
//        } else if (object instanceof Error error) {
//            return new ErrorHandlerImpl(error);
//        }
//
//        throw new RuntimeException("Couldn't process the server response: " + object);
//    }


}
