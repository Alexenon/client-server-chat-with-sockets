import chat.EncryptUtils;
import chat.client.models.EncryptedMessage;
import chat.client.models.User;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;

public class MessageEncryptDecryptTest {

    @Test
    public void testEncryptedMessage() {
        String text = "Today is Saturday";
        SecretKey secretKey = EncryptUtils.generateSecretKey();
        EncryptedMessage encryptedMessage = new EncryptedMessage(text, secretKey);
        String encryptedText = encryptedMessage.getText();
        String decryptedText = encryptedMessage.getText(secretKey);
        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
    }

    @Test
    public void testEncryptedMessageWithUser() {
        String text = "Hi Maria, this is Jacob";
        User maria = new User("Maria");
        User jacob = new User("Jacob");
        SecretKey secretKey = EncryptUtils.generateSecretKey();

        EncryptedMessage encryptedMessage = new EncryptedMessage(text, maria, jacob, secretKey);
        String encryptedText = encryptedMessage.getText();
        String decryptedText = encryptedMessage.getText(jacob.getPrivateKey());

        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
    }


}
