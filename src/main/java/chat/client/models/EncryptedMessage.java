package chat.client.models;

import chat.utils.ServerConfiguration;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.Arrays;

public class EncryptedMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1414927498819791395L;

    private final byte[] encryptedMessage;
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
        return new String(encryptedMessage, StandardCharsets.UTF_8);
    }

    public String getText(SecretKey secretKey) {
        return new String(decrypt(encryptedMessage, secretKey));
    }

    public String getText(PrivateKey privateKey) {
        return "";
    }

    private byte[] encrypt(String text, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedMessage = cipher.doFinal(text.getBytes());
            byte[] combined = new byte[iv.length + encryptedMessage.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedMessage, 0, combined, iv.length, encryptedMessage.length);
            return combined;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] decrypt(byte[] encryptedMessage, SecretKey secretKey) {
        try {
            byte[] key = secretKey.getEncoded();
            byte[] iv = Arrays.copyOfRange(encryptedMessage, 0, 16); // IV is 16 bytes long
            byte[] encryptedMessageShorted = Arrays.copyOfRange(encryptedMessage, 16, encryptedMessage.length);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            return cipher.doFinal(encryptedMessageShorted);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
               "text='" + getText() + '\'' +
               ", author=" + getAuthor() +
               ", receiver=" + getReceiver() +
               ", dateTime=" + getDateTime().format(ServerConfiguration.DATE_TIME_FORMATTER) +
               '}';
    }
}
