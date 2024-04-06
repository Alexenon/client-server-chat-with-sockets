package chat.handlers.input.parsers;

import chat.models.commands.Command;
import chat.models.commands.CommandType;
import chat.models.commands.HelpCommand;
import chat.models.errors.CommandParseException;
import chat.ui.ChatLayout;

import java.util.Arrays;

/**
 * <ul>
 *  <li>FORMAT: {@code "/help"} - Basic help command with common description
 *  <li>FORMAT: {@code /help <commandName>} - Help command for a specific command
 * </ul>
 * */
public class HelpCommandParser extends CommandParser {
    private final static int MAX_NUMBER_OF_ARGS = 1;

    private final ChatLayout chatLayout;

    public HelpCommandParser(ChatLayout chatLayout) {
        this.chatLayout = chatLayout;
    }

    @Override
    public Command parse(String input) throws CommandParseException {
        String commandNameForHelp = extractCommandForHelp(input);

        if (!isCommandForHelpValid(commandNameForHelp))
            throw new CommandParseException("Invalid command name for help \"%s\". %s"
                    .formatted(commandNameForHelp, viewListOfAllCommands));

        return new HelpCommand(chatLayout, commandNameForHelp);
    }

    private String extractCommandForHelp(String input) throws CommandParseException {
        String[] params = getParamsFromInput(input);

        if (params.length > MAX_NUMBER_OF_ARGS)
            throw new CommandParseException(incorrectNumArgsMsg);

        return Arrays.stream(params).findFirst().orElse("");
    }

    private boolean isCommandForHelpValid(String commandNameForHelp) {
        if (commandNameForHelp.isEmpty()) return true;

        return Arrays.stream(CommandType.values())
                .map(c -> c.toString().toLowerCase())
                .anyMatch(s -> s.equals(commandNameForHelp));
    }

}
