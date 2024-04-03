package chat.handlers.response;

import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;

import javax.crypto.SecretKey;

public class ResponseHandlerFactory {
    private User user;
    private SecretKey secretKey;

    public ResponseHandlerFactory(User user, SecretKey secretKey) {
        this.user = user;
        this.secretKey = secretKey;
    }

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

    public void setUser(User user) {
        this.user = user;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}




