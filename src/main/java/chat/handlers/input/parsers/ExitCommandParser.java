package chat.handlers.input.parsers;

import chat.models.commands.Command;
import chat.models.commands.ExitCommand;
import chat.models.errors.CommandParseException;
import chat.ui.ChatLayout;

public class ExitCommandParser extends CommandParser {

    private final ChatLayout chatLayout;

    public ExitCommandParser(ChatLayout chatLayout) {
        this.chatLayout = chatLayout;
    }

    @Override
    public Command parse(String input) throws CommandParseException {
        if (!input.equalsIgnoreCase("/exit"))
            throw new CommandParseException("Incorrect command \"%s\". %s".formatted(input, viewListOfAllCommands));

        return new ExitCommand(chatLayout);
    }
}
