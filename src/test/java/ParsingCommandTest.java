import chat.models.commands.parsers.HelpCommandParser;
import chat.models.commands.Command;
import chat.models.commands.CommandType;
import chat.models.commands.HelpCommand;
import chat.models.errors.CommandParseException;
import chat.ui.ChatLayout;
import org.junit.Assert;
import org.junit.Test;

public class ParsingCommandTest {

    ChatLayout chatLayout = null;

    @Test
    public void commandWithoutParamsTest() throws CommandParseException {
        String commandWithoutParams = "/help";
        HelpCommandParser helpCommandParser = new HelpCommandParser(chatLayout);
        Command helpCommand = helpCommandParser.parse(commandWithoutParams);

        Assert.assertTrue(helpCommand instanceof HelpCommand);
        Assert.assertEquals("help", helpCommand.getName());
        Assert.assertEquals(CommandType.HELP, helpCommand.getType());
        Assert.assertEquals(0, helpCommand.getParams().length);
    }

    @Test
    public void commandWithParamsTest() throws CommandParseException {
        String commandWithParams = "/help encrypt";

        HelpCommandParser helpCommandParser = new HelpCommandParser(chatLayout);
        Command helpCommand = helpCommandParser.parse(commandWithParams);

        Assert.assertTrue(helpCommand instanceof HelpCommand);
        Assert.assertEquals("help", helpCommand.getName());
        Assert.assertEquals(CommandType.HELP, helpCommand.getType());
        Assert.assertEquals(1, helpCommand.getParams().length);
    }

    @Test(expected = CommandParseException.class)
    public void commandTooManyArgsTest() throws CommandParseException {
        String commandWithParams = "/help encrypt invalid";
        HelpCommandParser helpCommandParser = new HelpCommandParser(chatLayout);
        helpCommandParser.parse(commandWithParams);
    }


}
