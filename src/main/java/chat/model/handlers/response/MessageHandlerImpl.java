package chat.model.handlers.response;

import chat.model.Message;
import chat.model.User;
import chat.utils.UserComparator;

import java.util.Objects;

// user1 -> null
// user1 -> user2
// null -> null
public record MessageHandlerImpl(Message message) implements ResponseHandler {
    @Override
    public String handleResult() {
        User author = message.getAuthor();
        User receiver = message.getReceiver();
        String text = message.getText();

        // Check if author exists and is not the same as receiver
        if (receiver != null && Objects.compare(author, receiver, new UserComparator()) != 0)
            return receiver.getUsername() + ": " + text;

        if(author != null)
            return author.getUsername() + ": " + text;


        return text;
    }
}