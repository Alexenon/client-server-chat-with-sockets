package chat.client.handlers.parsers;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.models.commands.Command;
import chat.client.models.commands.server.ViewMembersCommand;
import chat.utils.errors.CommandParseException;

public class MembersCommandParser extends CommandParser {

    private final InputSendingHandler inputSendingHandler;

    public MembersCommandParser(InputSendingHandler inputSendingHandler) {
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public Command parse(String input) throws CommandParseException {
        if (!input.equalsIgnoreCase("/members"))
            throw new CommandParseException("Invalid command \"%s\". %s".formatted(input, viewListOfAllCommands));

        return new ViewMembersCommand(inputSendingHandler);
    }
}
