import chat.handlers.input.convertors.InputConvertor;
import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;
import chat.models.commands.ExitCommand;
import chat.models.commands.HelpCommand;
import chat.models.errors.InvalidCommandException;
import chat.models.errors.InternalError;
import chat.models.errors.StatusCode;
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
        InputConvertor inputConvertor = new InputConvertor(author, secretKey);
        Object convertedObject = inputConvertor.convertIntoObject(text, false);

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

        InputConvertor inputConvertor = new InputConvertor(author, secretKey);
        Object convertedObject = inputConvertor.convertIntoObject(text, false);

        Assert.assertTrue(convertedObject instanceof Message);

        Message message = (Message) convertedObject;
        Assert.assertEquals(message.getText(), expectedText);
        Assert.assertEquals(message.getAuthor(), author);
        Assert.assertEquals(message.getReceiver(), expectedReceiver);
    }

    @Test
    public void testEncryptedPublicMessage() {
        String text = "This is a public message";
        InputConvertor inputConvertor = new InputConvertor(author, secretKey);
        Object convertedObject = inputConvertor.convertIntoObject(text, true);

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

        InputConvertor inputConvertor = new InputConvertor(author, secretKey);
        Object convertedObject = inputConvertor.convertIntoObject(text, true);

        Assert.assertTrue(convertedObject instanceof EncryptedMessage);

        EncryptedMessage message = (EncryptedMessage) convertedObject;
        Assert.assertNotEquals(message.getText(), text);
        Assert.assertEquals(message.getText(secretKey), expectedText);
        Assert.assertEquals(message.getAuthor(), author);
        Assert.assertEquals(message.getReceiver(), expectedReceiver);
    }

//    @Test
//    public void testValidCommands() throws InvalidCommandException {
//        String help = "/help";
//        String exit = "/exit";
//
//        InputConvertor inputConvertor = new InputConvertor(author, secretKey);
//        Object helpObj = inputConvertor.convertIntoObject(help, false);
//        Object exitObj = inputConvertor.convertIntoObject(exit, false);
//
//        Assert.assertTrue(helpObj instanceof HelpCommand);
//        Assert.assertTrue(exitObj instanceof ExitCommand);
//
//        HelpCommand helpCommand = (HelpCommand) helpObj;
//        ExitCommand exitCommand = (ExitCommand) exitObj;
//
//        Assert.assertTrue(helpCommand.isValid());
//        Assert.assertTrue(exitCommand.isValid());
//
//        System.out.println(helpCommand.getResult());
//        System.out.println(exitCommand.getResult());
//    }

//    @Test
//    public void testInvalidCommand() {
//        String me = "/me";
//        InputConvertor inputConvertor = new InputConvertor(author, secretKey);
//        Object object = inputConvertor.convertIntoObject(me, false);
//
//        Assert.assertTrue(object instanceof InternalError);
//        InternalError error = (InternalError) object;
//        Assert.assertEquals(error.getStatusCode(), StatusCode.BAD_REQUEST);
//        Assert.assertTrue(error.getErrorMessage().startsWith("Invalid command"));
//    }
//
//    @Test
//    public void testInvalidHelpCommand() throws InvalidCommandException {
//        String help = "/help me";
//        InputConvertor inputConvertor = new InputConvertor(author, secretKey);
//        Object object = inputConvertor.convertIntoObject(help, false);
//
//        Assert.assertTrue(object instanceof HelpCommand);
//        HelpCommand helpCommand = (HelpCommand) object;
//        String result = helpCommand.getResult();
//        Assert.assertFalse(helpCommand.isValid());
//        Assert.assertTrue(result.startsWith("Invalid command"));
//    }

}
