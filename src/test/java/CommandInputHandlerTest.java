import chat.handlers.input.command.CommandInputHandler;
import chat.models.commands.Command;
import chat.models.commands.InvalidCommandException;
import org.junit.Test;

public class CommandInputHandlerTest {

    @Test
    public void testHelpCommand() throws InvalidCommandException {
        String commandName = "/help";

        CommandInputHandler commandInputHandler = new CommandInputHandler(commandName);

        Command command = commandInputHandler.getCommand();
        command.execute();
    }

}
