import chat.EncryptUtils;
import chat.client.models.Message;
import chat.client.models.User;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.security.KeyPair;

public class EncryptDecryptTest {

    @Test
    public void testSymetricEncryption() {
        String text = "Today is Saturday";
        SecretKey secretKey = EncryptUtils.generateSecretKey();
        String encryptedText = EncryptUtils.encrypt(text, secretKey);
        String decryptedText = EncryptUtils.decrypt(encryptedText, secretKey);
        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
    }

    @Test
    public void testAsymetricEncryption() {
        String text = "Today is Saturday";
        KeyPair receiverKeyPair = EncryptUtils.generateKeyPair();
        String encryptedText = EncryptUtils.encrypt(text, receiverKeyPair.getPublic());
        String decryptedText = EncryptUtils.decrypt(encryptedText, receiverKeyPair.getPrivate());
        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
    }

    @Test
    public void testResetSecretKey() {
        String text = "Today is Saturday";
        SecretKey initialKey = EncryptUtils.generateSecretKey();

        String encryptedTextWithOldKey = EncryptUtils.encrypt(text, initialKey);
        String decryptedTextWithOldKey = EncryptUtils.decrypt(encryptedTextWithOldKey, initialKey);

        // Resetting secret key
        SecretKey newSecretKey = EncryptUtils.generateSecretKey();
        String encryptedTextWithNewKey = EncryptUtils.encrypt(text, newSecretKey);
        String decryptedTextWithNewKey = EncryptUtils.decrypt(encryptedTextWithNewKey, newSecretKey);

        Assert.assertNotEquals("The secret keys should be different after resetting them", initialKey, newSecretKey);
        Assert.assertNotEquals("The encrypted texts with different keys should not be the same", encryptedTextWithNewKey, encryptedTextWithOldKey);
        Assert.assertEquals("Encrypted text with old key should be decrypted with old secret key", text, decryptedTextWithOldKey);
        Assert.assertEquals("Encrypted text with new key should be decrypted with new secret key", text, decryptedTextWithNewKey);
        Assert.assertThrows("Encrypted text with old key cannot be decrypted with new secret key",
                RuntimeException.class, () -> EncryptUtils.decrypt(encryptedTextWithOldKey, newSecretKey));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testPublicEncryptedMessage() {
        String text = "Some simple text";
        User author = new User("Author");
        User receiver = null;
        SecretKey secretKey = EncryptUtils.generateSecretKey();

        Message encryptedMessage = new Message(text, author, receiver, secretKey);
        String encryptedText = encryptedMessage.getText();
        String decryptedText = encryptedMessage.getText(secretKey);
        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
        Assert.assertThrows("Decryption should be done just with Secret Key",
                RuntimeException.class, () -> encryptedMessage.getText(receiver.getPrivateKey()));
    }

    @Test
    public void testPrivateEncryptedMessage() {
        String text = "Some simple text";
        User author = new User("Author");
        User receiver = new User("Receiver");
        SecretKey secretKey = EncryptUtils.generateSecretKey();

        Message encryptedMessage = new Message(text, author, receiver, secretKey);
        String encryptedText = encryptedMessage.getText();
        String decryptedText = encryptedMessage.getText(receiver.getPrivateKey());
        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
        Assert.assertThrows("Decryption should be done just with Secret Key",
                RuntimeException.class, () -> encryptedMessage.getText(secretKey));
    }

}
