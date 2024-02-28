package chat.model;

import java.io.Serial;
import java.io.Serializable;

public class EncryptedMessage extends Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1414927498819791395L;

    private final String decryptKey;

    public EncryptedMessage(String text, User author, User receiver, String decryptKey) {
        super(text, author, receiver);
        this.decryptKey = decryptKey;
    }

    public String getDecryptKey() {
        return decryptKey;
    }

    @Override
    public String toString() {
        return "EncryptedMessage{" +
               "text='" + text + '\'' +
               ", author=" + author +
               ", receiver=" + receiver +
               ", decryptKey='" + decryptKey + '\'' +
               ", dateTime=" + getDateTimeFormatted() +
               '}';
    }
}
