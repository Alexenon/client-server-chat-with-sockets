package chat.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class EncryptedMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1414927498819791395L;

    private final String text;
    private final User author;
    private final User receiver;
    private final LocalDateTime dateTime;
    private final String encryptKey;

    public EncryptedMessage(String text) {
        this(text, null, null);
    }

    public EncryptedMessage(String text, User author, User receiver) {
        this(text, author, receiver, null);
    }

    public EncryptedMessage(String text, User author, User receiver, String encryptKey) {
        this.text = text;
        this.author = author;
        this.receiver = receiver;
        this.encryptKey = encryptKey;
        this.dateTime = LocalDateTime.now();
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public User getReceiver() {
        return receiver;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "EncryptedMessage{" +
               "text='" + text + '\'' +
               ", author=" + author +
               ", receiver=" + receiver +
               ", decryptKey='" + encryptKey + '\'' +
               ", dateTime=" + dateTime +
               '}';
    }
}
