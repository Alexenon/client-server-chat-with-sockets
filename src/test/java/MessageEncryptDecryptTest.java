import chat.EncryptUtils;
import chat.client.models.Message;
import chat.client.models.User;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;

public class MessageEncryptDecryptTest {

    @Test
    public void testMessage() {
        String text = "Today is Saturday";
        SecretKey secretKey = EncryptUtils.generateSecretKey();
        Message Message = new Message(text, null, null, secretKey);
        String encryptedText = Message.getText();
        String decryptedText = Message.getText(secretKey);
        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
    }

    @Test
    public void testMessageWithUser() {
        String text = "Hi Maria, this is Jacob";
        User maria = new User("Maria");
        User jacob = new User("Jacob");
        SecretKey secretKey = EncryptUtils.generateSecretKey();

        Message Message = new Message(text, maria, jacob, secretKey);
        String encryptedText = Message.getText();
        String decryptedText = Message.getText(jacob.getPrivateKey());

        Assert.assertNotEquals("Encrypted text should not match the original text", encryptedText, decryptedText);
        Assert.assertEquals("Decrypted text should match the original text", text, decryptedText);
    }


}
