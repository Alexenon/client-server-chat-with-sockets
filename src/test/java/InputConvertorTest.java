import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;
import chat.models.commands.ExitCommand;
import chat.models.commands.HelpCommand;
import chat.models.commands.InvalidCommandException;
import chat.models.temp.InputConvertor;
import chat.sever.ServerManager;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;

public class InputConvertorTest {

    User author = new User("author");
    SecretKey secretKey = ServerManager.initiateGroupKey();

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

    @Test
    public void testEncryptedPrivateMessage() {
        String text = "Dan: This is a private message";
        User expectedReceiver = new User("Dan");
        String expectedText = "This is a private message";

        InputConvertor inputConvertor = new InputConvertor(author, secretKey, true);
        Object convertedObject = inputConvertor.convertIntoObject(text);

        Assert.assertTrue(convertedObject instanceof EncryptedMessage);

        EncryptedMessage message = (EncryptedMessage) convertedObject;
        Assert.assertNotEquals(message.getText(), text);
        Assert.assertEquals(message.getText(secretKey), expectedText);
        Assert.assertEquals(message.getAuthor(), author);
        Assert.assertEquals(message.getReceiver(), expectedReceiver);
    }

    @Test
    public void testCommands() throws InvalidCommandException {
        String help = "/help";
        String exit = "/exit";

        InputConvertor inputConvertor = new InputConvertor(author, secretKey, false);
        Object helpObj = inputConvertor.convertIntoObject(help);
        Object exitObj = inputConvertor.convertIntoObject(exit);

        Assert.assertTrue(helpObj instanceof HelpCommand);
        Assert.assertTrue(exitObj instanceof ExitCommand);

        HelpCommand helpCommand = (HelpCommand) helpObj;
        ExitCommand exitCommand = (ExitCommand) exitObj;

        Assert.assertTrue(helpCommand.isValid());
        Assert.assertTrue(exitCommand.isValid());

        System.out.println(helpCommand.getResult());
        System.out.println(exitCommand.getResult());
    }

}
