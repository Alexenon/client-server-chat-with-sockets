package chat.client.models;

import chat.EncryptUtils;
import chat.utils.ServerConfiguration;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.security.PrivateKey;
import java.text.MessageFormat;
import java.time.LocalDateTime;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 8322154030015983245L;

    private final String text;
    private final User author;
    private final User receiver;
    private final LocalDateTime dateTime;
    private final boolean isEncrypted;

    public Message(String text) {
        this(text, null, null);
    }

    public Message(String text, User author, User receiver) {
        this.text = text;
        this.author = author;
        this.receiver = receiver;
        this.dateTime = LocalDateTime.now();
        isEncrypted = false;
    }

    public Message(String text, User author, User receiver, SecretKey secretKey) {
        this.dateTime = LocalDateTime.now();
        this.author = author;
        this.receiver = receiver;
        this.text = encrypt(text, secretKey);
        isEncrypted = true;
    }

    public String encrypt(String text, SecretKey secretKey) {
        return receiver == null
                ? EncryptUtils.encrypt(text, secretKey)
                : EncryptUtils.encrypt(text, receiver.getPublicKey());
    }

    public String getText() {
        return text;
    }

    public String getText(SecretKey secretKey) {
        return EncryptUtils.decrypt(text, secretKey);
    }

    public String getText(PrivateKey privateKey) {
        return EncryptUtils.decrypt(text, privateKey);
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

    public String getDateTimeFormatted() {
        return dateTime.format(ServerConfiguration.DATE_TIME_FORMATTER);
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Message'{'text=''{0}'', author={1}, receiver={2}, dateTime={3}'}'",
                text.replaceAll("\n", " "),
                getAuthor(),
                getReceiver(),
                getDateTimeFormatted());
    }
}
