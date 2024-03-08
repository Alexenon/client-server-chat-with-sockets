import chat.models.EncryptedMessage;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class EncryptionDecryptionTest {
    private final String message = "Hello, this should be encrypted";
    private final EncryptedMessage encryptedMessage;
    private final SecretKey secretKey;

    public EncryptionDecryptionTest() {
        secretKey = generateAESKey();
        encryptedMessage = new EncryptedMessage(message, null, null, secretKey);
    }

    @Test
    public void testEncryption() {
        String encryptedMessageText = encryptedMessage.getText();
        System.out.println("message = " + message);
        System.out.println("encryptedMessageText = " + encryptedMessageText);
        Assert.assertNotEquals(message, encryptedMessageText);
    }

    @Test
    public void testDecryption() {
        String decryptedMessageText = encryptedMessage.getText(secretKey);
        System.out.println("message = " + message);
        System.out.println("decryptedMessageText = " + decryptedMessageText);
        Assert.assertEquals(message, decryptedMessageText);
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
