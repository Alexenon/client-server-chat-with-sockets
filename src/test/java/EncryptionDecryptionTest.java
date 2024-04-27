import chat.EncryptUtils;
import chat.client.models.EncryptedMessage;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;

public class EncryptionDecryptionTest {
    private final String message = "Hello, this should be encrypted";
    private final SecretKey secretKey;
    private final EncryptedMessage encryptedMessage;

    public EncryptionDecryptionTest() {
        secretKey = EncryptUtils.initiateGroupKey();
        encryptedMessage = new EncryptedMessage(message, secretKey);
    }

    @Test
    public void testEncryptUtils() {
        String text = "Today is Saturday";
        SecretKey secretKey = EncryptUtils.initiateGroupKey();
        String encryptedText = EncryptUtils.encrypt(text, secretKey);
        String decryptedText = EncryptUtils.decrypt(encryptedText, secretKey);
        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
    }

    @Test
    public void testEncryption() {
        String encryptedMessageText = encryptedMessage.getText();
        System.out.println("message = " + message);
        System.out.println("encryptedMessageText = " + encryptedMessageText);
        System.out.println(encryptedMessage);
        Assert.assertNotEquals("The message should be encrypted", message, encryptedMessageText);
    }

    @Test
    public void testDecryption() {
        String decryptedMessageText = encryptedMessage.getText(secretKey);
        System.out.println("message = " + message);
        System.out.println("decryptedMessageText = " + decryptedMessageText);
        System.out.println(encryptedMessage);
        Assert.assertEquals("The decrypted message should match the original message", message, decryptedMessageText);
    }

    @Test
    public void testResetSecretKey() {
        String text = "Today is Saturday";
        SecretKey initialKey = EncryptUtils.initiateGroupKey();

        String encryptedTextWithOldKey = EncryptUtils.encrypt(text, initialKey);
        String decryptedTextWithOldKey = EncryptUtils.decrypt(encryptedTextWithOldKey, initialKey);

        // Resetting secret key
        SecretKey newSecretKey = EncryptUtils.initiateGroupKey();
        String encryptedTextWithNewKey = EncryptUtils.encrypt(text, newSecretKey);
        String decryptedTextWithNewKey = EncryptUtils.decrypt(encryptedTextWithNewKey, newSecretKey);

        Assert.assertNotEquals("The secret keys should be different after resetting them", initialKey, newSecretKey);
        Assert.assertNotEquals("The encrypted texts with different keys should not be the same", encryptedTextWithNewKey, encryptedTextWithOldKey);
        Assert.assertEquals("Encrypted text with old key should be decrypted with old secret key", text, decryptedTextWithOldKey);
        Assert.assertEquals("Encrypted text with new key should be decrypted with new secret key", text, decryptedTextWithNewKey);
        Assert.assertThrows("Encrypted text with old key cannot be decrypted with new secret key",
                RuntimeException.class, () -> EncryptUtils.decrypt(encryptedTextWithOldKey, newSecretKey));
    }

}
