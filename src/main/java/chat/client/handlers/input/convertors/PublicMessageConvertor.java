package chat.client.handlers.input.convertors;

import chat.client.models.EncryptedMessage;
import chat.client.models.Message;
import chat.client.models.User;

import javax.crypto.SecretKey;

public record PublicMessageConvertor(User author, SecretKey secretKey, boolean shouldBeEncrypted) implements Convertor {

    @Override
    public Object getObjectFromInput(String input) {
        return shouldBeEncrypted
                ? new EncryptedMessage(input, author, null, secretKey)
                : new Message(input, author, null);
    }
}
