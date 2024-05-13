package chat.client.handlers.parsers;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.models.commands.Command;
import chat.client.models.commands.server.ViewConnectedUsersCommand;
import chat.utils.errors.CommandParseException;

public class ConnectedUsersCommandParser extends CommandParser {

    private final InputSendingHandler inputSendingHandler;

    public ConnectedUsersCommandParser(InputSendingHandler inputSendingHandler) {
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public Command parse(String input) throws CommandParseException {
        if (!input.equalsIgnoreCase("/users"))
            throw new CommandParseException("Invalid command \"%s\". %s".formatted(input, viewListOfAllCommands));

        return new ViewConnectedUsersCommand(inputSendingHandler);
    }
}
