import chat.models.commands.Command;
import chat.models.commands.HelpCommand;
import chat.models.commands.InvalidCommandException;
import org.junit.Assert;
import org.junit.Test;

public class LocalCommandsTest {

    @Test
    public void testHelpCommand() throws InvalidCommandException {
        Command standardHelpCommand = new HelpCommand("/help");
        Assert.assertTrue(standardHelpCommand.isValid());
        standardHelpCommand.execute();
        Assert.assertFalse(standardHelpCommand.getResult().startsWith("Invalid command"));

        Command helpCommandForEncrypt = new HelpCommand("/help encrypt");
        Assert.assertTrue(helpCommandForEncrypt.isValid());
        helpCommandForEncrypt.execute();
        Assert.assertFalse(helpCommandForEncrypt.getResult().startsWith("Invalid command"));
    }

}
