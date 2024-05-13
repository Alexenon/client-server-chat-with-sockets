package chat.client.handlers.response;

import chat.client.models.EncryptedMessage;
import chat.client.models.User;
import chat.client.ui.ChatLayout;
import chat.utils.ServerConfiguration;
import chat.utils.UserComparator;

import javax.crypto.SecretKey;
import java.awt.*;
import java.util.Objects;

/**
 * Class designed to handle message response received from server, into a String representation
 * that can be displayed to the user
 */
public class EncryptedMessageHandlerImpl extends ResponseHandler<EncryptedMessage> {
    private static final Color COLOR_AUTHOR = Color.CYAN;
    private static final Color COLOR_RECEIVER = Color.MAGENTA;
    private static final Color COLOR_RECEIVER_PRIVATE_MESSAGE = Color.YELLOW;
    private static final UserComparator USER_COMPARATOR = new UserComparator();

    public EncryptedMessageHandlerImpl(ChatLayout chatLayout, User user, SecretKey secretKey) {
        super(chatLayout, user, secretKey);
    }

    @Override
    public void handleResult(EncryptedMessage message) {
        User author = message.getAuthor();
        User receiver = message.getReceiver();
        String text = getDisplayText(message);

        // Private message
        if (receiver != null) {
            if (Objects.compare(user, author, USER_COMPARATOR) == 0) {
                text = "(privmsg -> %s) %s".formatted(receiver.getUsername(), text);
                chatLayout.updateChatArea(text, COLOR_AUTHOR);
            } else {
                text = "(privmsg) " + text;
                chatLayout.updateChatArea(text, COLOR_RECEIVER_PRIVATE_MESSAGE);
            }
            return;
        }

        // Message with author
        if (author != null) {
            if (Objects.compare(user, author, USER_COMPARATOR) == 0) {
                chatLayout.updateChatArea(text, COLOR_AUTHOR);
            } else {
                chatLayout.updateChatArea(text, COLOR_RECEIVER);
            }
            return;
        }

        chatLayout.updateChatArea(text);
    }

    private String getDisplayText(EncryptedMessage message) {
        String text = getTextDecrypted(message);
        String time = getMessageTime(message);

        if (message.getAuthor() == null)
            return "*** " + text;

        User author = message.getAuthor();
        return Objects.compare(user, author, USER_COMPARATOR) == 0
                ? "{encrypted} [%s] %s".formatted(time, text)
                : "{encrypted} [%s] <%s>: %s".formatted(time, author.getUsername(), text);
    }

    public String getMessageTime(EncryptedMessage message) {
        return message.getDateTime().format(ServerConfiguration.TIME_FORMATTER);
    }
    private String getTextDecrypted(EncryptedMessage message) {
        return message.getReceiver() == null
                ? message.getText(secretKey)
                : message.getText(message.getReceiver().getPrivateKey());
    }

}