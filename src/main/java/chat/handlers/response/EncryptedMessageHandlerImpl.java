package chat.handlers.response;

import chat.models.EncryptedMessage;
import chat.models.User;
import chat.utils.UserComparator;

import java.security.Key;
import java.security.PrivateKey;
import java.util.Objects;

/**
 * Class design to handle message response received from server into a String representation
 * that can be displayed to the user
 * */
public record EncryptedMessageHandlerImpl(EncryptedMessage message, User user, PrivateKey privateKey) implements ResponseHandler {
    @Override
    public String handleResult() {
        User author = message.getAuthor();
        User receiver = message.getReceiver();
        String text = message.getText(privateKey);

        // Check if author exists and is not the same as current user
        if (author != null && Objects.compare(author, user, new UserComparator()) != 0)
            return author.getUsername() + ": " + text;

        // Check if author exists and is not the same as receiver
        if (receiver != null && Objects.compare(author, receiver, new UserComparator()) != 0)
            return receiver.getUsername() + ": " + text;

        return text;
    }
}