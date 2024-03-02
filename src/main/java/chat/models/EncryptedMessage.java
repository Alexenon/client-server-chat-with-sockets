package chat.models;

import javax.crypto.Cipher;
import java.io.Serial;
import java.io.Serializable;
import java.security.*;
import java.time.LocalDateTime;
import java.util.Base64;

public class EncryptedMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1414927498819791395L;

    private final String text;
    private final User author;
    private final User receiver;
    private final LocalDateTime dateTime;

    public EncryptedMessage(String text, User author, User receiver, PublicKey publicKey) {
        this.text = encryptMessage(text, publicKey);
        this.author = author;
        this.receiver = receiver;
        this.dateTime = LocalDateTime.now();
    }

    private String encryptMessage(String message, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getText() {
        return text;
    }

    public String getText(PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
               ", dateTime=" + dateTime +
               '}';
    }
}
