package chat.client.handlers.response;

import chat.client.models.EncryptedMessage;
import chat.client.models.User;
import chat.client.ui.ChatLayout;
import chat.utils.UserComparator;

import javax.crypto.SecretKey;
import java.util.Objects;

/**
 * Class designed to handle message response received from server, into a String representation
 * that can be displayed to the user
 */
public class EncryptedMessageHandlerImpl extends ResponseHandler<EncryptedMessage> {

    public EncryptedMessageHandlerImpl(ChatLayout chatLayout, User user, SecretKey secretKey) {
        super(chatLayout, user, secretKey);
    }

    @Override
    public void handleResult(EncryptedMessage message) {
        String messageToBeDisplayed = getText(message);
        chatLayout.updateChatArea(messageToBeDisplayed);
    }

    public String getText(EncryptedMessage message) {
        User author = message.getAuthor();
        User receiver = message.getReceiver();
        String text = getTextDecrypted(message, user, secretKey);

        // Check if author exists and is not the same as current user
        if (author != null && Objects.compare(author, user, new UserComparator()) != 0)
            return author.getUsername() + ": " + text;

        // Check if author exists and is not the same as receiver
        if (receiver != null && Objects.compare(author, receiver, new UserComparator()) != 0)
            return receiver.getUsername() + ": " + text;

        return text;
    }

    private String getTextDecrypted(EncryptedMessage message, User user, SecretKey secretKey) {
        return message.getReceiver() == null
                ? message.getText(secretKey)
                : message.getText(user.getPrivateKey());
    }

}