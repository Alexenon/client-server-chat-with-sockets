package chat.models.temp;

import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;

import javax.crypto.SecretKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record PrivateMessageConvertor(User author,
                                      SecretKey secretKey,
                                      boolean shouldBeEncrypted) implements Convertor {

    @Override
    public Object getObjectFromInput(String input) {
        Pattern pattern = Pattern.compile(InputConvertor.PRIVATE_MESSAGE_REGEX);
        Matcher matcher = pattern.matcher(input);

        String receiverUsername = matcher.group(1);
        String messageText = matcher.group(2).trim();

        User receiver = receiverUsername == null
                ? null
                : new User(receiverUsername);

        return shouldBeEncrypted ?
                new EncryptedMessage(messageText, author, receiver, secretKey)
                : new Message(messageText, author, receiver);
    }

}