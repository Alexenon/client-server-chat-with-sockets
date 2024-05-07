package chat.client.handlers.parsers;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.models.commands.Command;
import chat.client.models.commands.server.ViewUserInfoCommand;
import chat.utils.errors.CommandParseException;

import java.util.Arrays;

/**
 * Allowed Formats:
 * <ul>
 *  <li>FORMAT: {@code "/info"} - Information about current user - the user that typed this command
 *  <li>FORMAT: {@code /info <username>} - Information about user with provided username
 * </ul>
 */
public class InfoUserCommandParser extends CommandParser {
    private final static int MAX_NUMBER_OF_ARGS = 1;

    private final InputSendingHandler inputSendingHandler;

    public InfoUserCommandParser(InputSendingHandler inputSendingHandler) {
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public Command parse(String input) throws CommandParseException {
        String[] params = getParamsFromInput(input);
        if (params.length > MAX_NUMBER_OF_ARGS)
            throw new CommandParseException(incorrectNumArgsMsg);

        String usernameForUserToView = Arrays.stream(params).findFirst().orElse(null);

        return new ViewUserInfoCommand(inputSendingHandler, usernameForUserToView);
    }
}
