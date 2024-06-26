package chat.client.handlers.parsers;

import chat.client.models.commands.Command;
import chat.utils.errors.CommandParseException;

import java.util.Arrays;
import java.util.Objects;

public abstract class CommandParser {
    protected static final String incorrectArgsMsg = "Incorrect argument format";
    protected static final String incorrectNumArgsMsg = "Incorrect number of arguments";
    protected static final String viewListOfAllCommands = "To view the list of all valid commands, simply type \"/help\".";

    protected static int MAX_NUMBER_OF_ARGS = 2;

    public abstract Command parse(String input) throws CommandParseException;

    protected String[] getParamsFromInput(String input) {
        return Arrays.stream(input.split(" "))
                .skip(1)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

}
