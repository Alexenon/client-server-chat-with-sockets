package chat.client.handlers.response;

import chat.client.models.User;
import chat.client.ui.ChatLayout;

import javax.crypto.SecretKey;

/**
 * Class designed to handle an object received from the server {@link T}. <br>
 * Most of the time the processed response should be provided to the user, so he could understand what happened. <br>
 * Example: {@link MessageHandlerImpl}
 * */
public abstract class ResponseHandler<T> {
    protected final ChatLayout chatLayout;
    protected final User user;
    protected SecretKey secretKey;

    public ResponseHandler(ChatLayout chatLayout, User user, SecretKey secretKey) {
        this.chatLayout = chatLayout;
        this.user = user;
        this.secretKey = secretKey;
    }

    abstract void handleResult(T object);

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}