package chat.client.handlers.parsers;

import chat.client.models.commands.Command;
import chat.client.models.commands.client.ExitCommand;
import chat.utils.errors.CommandParseException;
import chat.client.ui.ChatLayout;

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
