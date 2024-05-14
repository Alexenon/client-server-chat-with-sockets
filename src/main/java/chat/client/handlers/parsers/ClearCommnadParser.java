package chat.client.handlers.parsers;

import chat.client.models.commands.Command;
import chat.client.models.commands.client.ClearCommand;
import chat.client.ui.ChatLayout;
import chat.utils.errors.CommandParseException;

public class ClearCommnadParser extends CommandParser {

    private final ChatLayout chatLayout;

    public ClearCommnadParser(ChatLayout chatLayout) {
        this.chatLayout = chatLayout;
    }

    @Override
    public Command parse(String input) throws CommandParseException {
        if (!input.equalsIgnoreCase("/clear"))
            throw new CommandParseException("Incorrect command \"%s\". %s".formatted(input, viewListOfAllCommands));

        return new ClearCommand(chatLayout);
    }
}
