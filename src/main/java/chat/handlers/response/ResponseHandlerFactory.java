package chat.handlers.response;

import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;

import javax.crypto.SecretKey;

public record ResponseHandlerFactory(User user, SecretKey secretKey) {

    public ResponseHandler createResponseHandler(Object object) {
        if (object instanceof Message message) {
            return new MessageHandlerImpl(message, user);
        } else if (object instanceof EncryptedMessage encryptedMessage) {
            return new EncryptedMessageHandlerImpl(encryptedMessage, user, secretKey);
        } else if (object instanceof Error error) {
            return new ErrorHandlerImpl(error);
        }

        throw new RuntimeException("Couldn't process the server response: " + object);
    }
}




