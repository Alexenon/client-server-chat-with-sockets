package chat.client.handlers.response;

import chat.client.models.EncryptedMessage;
import chat.client.models.Message;
import chat.client.models.User;
import chat.client.ui.ChatLayout;
import chat.utils.errors.ServerError;

import javax.crypto.SecretKey;

public class ResponseHandlerFactory {
    private final MessageHandlerImpl messageHandler;
    private final EncryptedMessageHandlerImpl encryptedMessageHandler;

    public ResponseHandlerFactory(ChatLayout chatLayout, User user, SecretKey secretKey) {
        this.messageHandler = new MessageHandlerImpl(chatLayout, user, secretKey);
        this.encryptedMessageHandler = new EncryptedMessageHandlerImpl(chatLayout, user, secretKey);
    }

    public void handleReceivedObjectFromServer(Object object) {
        if (object instanceof Message message) {
            messageHandler.handleResult(message);
            return;
        } else if (object instanceof EncryptedMessage encryptedMessage) {
            encryptedMessageHandler.handleResult(encryptedMessage);
            return;
        } else if (object instanceof ServerError error) {
            throw new RuntimeException(String.valueOf(error));
        }

        throw new RuntimeException("Couldn't process the server response: " + object);
    }

    public void setSecretKey(SecretKey secretKey) {
        messageHandler.setSecretKey(secretKey);
        encryptedMessageHandler.setSecretKey(secretKey);
    }
}




