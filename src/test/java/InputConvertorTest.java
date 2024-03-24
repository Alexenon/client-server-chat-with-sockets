import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;
import chat.models.temp.InputConvertor;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class InputConvertorTest {

    User author = new User("author");
    SecretKey secretKey = initiateGroupKey();

    @Test
    public void testPublicMessage() {
        String text = "This is a public message";
        InputConvertor inputConvertor = new InputConvertor(author, secretKey, false);
        Object convertedObject = inputConvertor.convertIntoObject(text);

        Assert.assertTrue(convertedObject instanceof Message);

        Message message = (Message) convertedObject;
        Assert.assertEquals(message.getText(), text);
        Assert.assertEquals(message.getAuthor(), author);
        Assert.assertNull(message.getReceiver());
    }

    @Test
    public void testPrivateMessage() {
        String text = "Dan: This is a private message";
        User expectedReceiver = new User("Dan");
        String expectedText = "This is a private message";

        InputConvertor inputConvertor = new InputConvertor(author, secretKey, false);
        Object convertedObject = inputConvertor.convertIntoObject(text);

        Assert.assertTrue(convertedObject instanceof Message);

        Message message = (Message) convertedObject;
        Assert.assertEquals(message.getText(), expectedText);
        Assert.assertEquals(message.getAuthor(), author);
        Assert.assertEquals(message.getReceiver(), expectedReceiver);
    }

    @Test
    public void testEncryptedPublicMessage() {
        String text = "This is a public message";
        InputConvertor inputConvertor = new InputConvertor(author, secretKey, true);
        Object convertedObject = inputConvertor.convertIntoObject(text);

        Assert.assertTrue(convertedObject instanceof EncryptedMessage);

        EncryptedMessage message = (EncryptedMessage) convertedObject;
        Assert.assertNotEquals(message.getText(), text);
        Assert.assertEquals(message.getText(secretKey), text);
        Assert.assertEquals(message.getAuthor(), author);
        Assert.assertNull(message.getReceiver());
    }

    private SecretKey initiateGroupKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException");
        }
    }
}
