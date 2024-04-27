package chat.client.models;

import chat.EncryptUtils;
import chat.utils.ServerConfiguration;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.security.PrivateKey;
import java.time.LocalDateTime;

public class EncryptedMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1414927498819791395L;

    private final String encryptedMessage;
    private final User author;
    private final User receiver;
    private final LocalDateTime dateTime;

    public EncryptedMessage(String text, SecretKey secretKey) {
        this(text, null, null, secretKey);
    }

    public EncryptedMessage(String text, User author, User receiver, SecretKey secretKey) {
        this.encryptedMessage = encrypt(text, secretKey);
        this.author = author;
        this.receiver = receiver;
        this.dateTime = LocalDateTime.now();
    }

    public String getText() {
        return encryptedMessage;
    }

    public String getText(SecretKey secretKey) {
        return decrypt(encryptedMessage, secretKey);
    }

    public String getText(PrivateKey privateKey) {
        return "";
    }

    private String encrypt(String text, SecretKey secretKey) {
        return EncryptUtils.encrypt(text, secretKey);
    }

    private String decrypt(String encryptedMessage, SecretKey secretKey) {
        return EncryptUtils.decrypt(encryptedMessage, secretKey);
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
               "text='" + getText() + '\'' +
               ", author=" + getAuthor() +
               ", receiver=" + getReceiver() +
               ", dateTime=" + getDateTime().format(ServerConfiguration.DATE_TIME_FORMATTER) +
               '}';
    }
}
