package chat.client.handlers.parsers;

import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.client.models.commands.client.HelpCommand;
import chat.utils.errors.CommandParseException;
import chat.client.ui.ChatLayout;

import java.util.Arrays;

/**
 * <ul>
 *  <li>FORMAT: {@code "/help"} - Basic help command with common description
 *  <li>FORMAT: {@code /help <commandName>} - Help command for a specific command
 * </ul>
 */
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
            throw new CommandParseException("%s \"%s\". %s"
                    .formatted(incorrectArgsMsg, commandNameForHelp, viewListOfAllCommands));

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
