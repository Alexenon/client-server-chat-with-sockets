import chat.models.commands.Command;
import chat.models.commands.CommandType;
import chat.models.commands.HelpCommand;
import org.junit.Assert;
import org.junit.Test;

public class CommandTest {

    @Test
    public void parsingCommandWithoutParams() {
        String commandWithoutParams = "/help";
        Command commandHelp = new HelpCommand(commandWithoutParams);

        Assert.assertEquals(CommandType.HELP, commandHelp.getType());
        Assert.assertEquals(0, commandHelp.getParams().length);
    }

    @Test
    public void parsingCommandWithParams() {
        String commandWithParams = "/help -i -u";
        Command commandHelp = new HelpCommand(commandWithParams);

        Assert.assertEquals(CommandType.HELP, commandHelp.getType());
        Assert.assertEquals(2, commandHelp.getParams().length);
    }


}
