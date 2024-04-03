package chat.handlers.input.convertors;

import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;

import javax.crypto.SecretKey;

public record PublicMessageConvertor(User author, SecretKey secretKey, boolean shouldBeEncrypted) implements Convertor {

    @Override
    public Object getObjectFromInput(String input) {
        return shouldBeEncrypted
                ? new EncryptedMessage(input, author, null, secretKey)
                : new Message(input, author, null);
    }
}
