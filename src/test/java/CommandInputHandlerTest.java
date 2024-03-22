import chat.handlers.input.command.CommandInputHandler;
import chat.models.commands.Command;
import chat.models.commands.InvalidCommandException;
import org.junit.Test;

public class CommandInputHandlerTest {

    @Test
    public void testHelpCommand() throws InvalidCommandException {
        String helpCommand = "/help";
        String helpEncrypt = helpCommand + " encrypt";
        String helpExit = helpCommand + " exit";
        String invalidCommand = helpCommand + " me";

        String[] commands = {helpCommand, helpEncrypt, helpExit, invalidCommand};

        for (String commandName : commands) {
            CommandInputHandler commandInputHandler = new CommandInputHandler(commandName);
            Command command = commandInputHandler.getCommand();
            command.execute();
            System.out.println(command.getResult());
        }

    }

}
