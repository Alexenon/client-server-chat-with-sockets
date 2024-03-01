package chat.models;

import java.io.Serial;
import java.io.Serializable;

public class EncryptedMessage extends Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1414927498819791395L;

    private final String encryptKey;

    public EncryptedMessage(String text) {
        this(text, null, null);
    }

    public EncryptedMessage(String text, User author, User receiver) {
        this(text, author, receiver, null);
    }

    public EncryptedMessage(String text, User author, User receiver, String encryptKey) {
        super(text, author, receiver);
        this.encryptKey = encryptKey;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    @Override
    public String toString() {
        return "EncryptedMessage{" +
               "text='" + text + '\'' +
               ", author=" + author +
               ", receiver=" + receiver +
               ", decryptKey='" + encryptKey + '\'' +
               ", dateTime=" + getDateTimeFormatted() +
               '}';
    }
}
