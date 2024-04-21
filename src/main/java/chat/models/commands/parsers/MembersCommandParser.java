package chat.models.commands.parsers;

import chat.handlers.input.InputSendingHandler;
import chat.models.commands.Command;
import chat.models.commands.server.MembersCommand;
import chat.models.errors.CommandParseException;

public class MembersCommandParser extends CommandParser {

    private final InputSendingHandler inputSendingHandler;

    public MembersCommandParser(InputSendingHandler inputSendingHandler) {
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public Command parse(String input) throws CommandParseException {
        if (!input.equalsIgnoreCase("/members"))
            throw new CommandParseException("Invalid command \"%s\". %s".formatted(input, viewListOfAllCommands));

        return new MembersCommand(inputSendingHandler);
    }
}
