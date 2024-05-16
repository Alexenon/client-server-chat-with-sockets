package chat.client.handlers.response;

import chat.client.models.Message;
import chat.client.models.User;
import chat.client.ui.ChatLayout;
import chat.utils.ServerConfiguration;
import chat.utils.UserComparator;

import javax.crypto.SecretKey;
import java.awt.*;
import java.util.Objects;

/**
 * Class design to handle message response received from server into a String representation
 * that can be displayed to the user
 */
public class MessageHandlerImpl extends ResponseHandler<Message> {
    private static final Color COLOR_AUTHOR = Color.CYAN;
    private static final Color COLOR_RECEIVER = Color.MAGENTA;
    private static final Color COLOR_RECEIVER_PRIVATE_MESSAGE = Color.YELLOW;
    private static final UserComparator USER_COMPARATOR = new UserComparator();

    public MessageHandlerImpl(ChatLayout chatLayout, User user, SecretKey secretKey) {
        super(chatLayout, user, secretKey);
    }

    @Override
    public void handleResult(Message message) {
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

    private String getDisplayText(Message message) {
        String text = getMessageText(message);
        String time = getMessageTime(message);

        if (message.getAuthor() == null)
            return "*** " + text;

        User author = message.getAuthor();
        return Objects.compare(user, author, USER_COMPARATOR) == 0
                ? "[%s] %s".formatted(time, text)
                : "[%s] <%s>: %s".formatted(time, author.getUsername(), text);
    }

    private String getMessageText(Message message) {
        if (!message.isEncrypted())
            return message.getText();

        return message.getReceiver() == null
                ? message.getText(secretKey)
                : message.getText(message.getReceiver().getPrivateKey());
    }

    public String getMessageTime(Message message) {
        return message.getDateTime().format(ServerConfiguration.TIME_FORMATTER);
    }

}