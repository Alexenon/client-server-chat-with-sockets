import chat.models.EncryptedMessage;
import chat.models.User;
import chat.utils.CipherManager;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class EncryptionDecryptionTest {
    private final String message = "Hello, this should be encrypted";
    private final SecretKey secretKey;
    private final EncryptedMessage encryptedMessage;

    public EncryptionDecryptionTest() {
        secretKey = generateAESKey();
        encryptedMessage = new EncryptedMessage(message, secretKey);
    }

    @Test
    public void testMessageEncryption() {
        String encryptedMessageText = encryptedMessage.getText();
        System.out.println("message = " + message);
        System.out.println("encryptedMessageText = " + encryptedMessageText);
        System.out.println(encryptedMessage);
        Assert.assertNotEquals(message, encryptedMessageText);
    }

    @Test
    public void testMessageDecryption() {
        String decryptedMessageText = encryptedMessage.getText(secretKey);
        System.out.println("message = " + message);
        System.out.println("decryptedMessageText = " + decryptedMessageText);
        System.out.println(encryptedMessage);
        Assert.assertEquals(message, decryptedMessageText);
    }

    @Test
    public void testWithKeyEncryption() {
        CipherManager cipherManager = CipherManager.getInstance();
        User user = new User("test");
        SecretKey encryptedKey = cipherManager.encryptSecretKey(secretKey, user.getPublicKey());
        SecretKey decryptedKey = cipherManager.decryptSecretKey(encryptedKey, user.getPrivateKey());

        String message = "This is a message";
        byte[] encryptedMessageBytes = cipherManager.encrypt(message, secretKey);
        byte[] decryptedMessageBytes = cipherManager.decrypt(encryptedMessageBytes, decryptedKey);
        String decryptedMessage = new String(decryptedMessageBytes);

        System.out.println(message);
        System.out.println(decryptedMessage);
        Assert.assertNotEquals(encryptedKey, decryptedKey);
        Assert.assertEquals(message, decryptedMessage);
    }

    private SecretKey generateAESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException");
        }
    }
}
