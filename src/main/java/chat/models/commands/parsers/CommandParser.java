package chat.models.commands.parsers;

import chat.models.commands.Command;
import chat.models.errors.CommandParseException;

import java.util.Arrays;

public abstract class CommandParser {
    protected static final String incorrectArgsMsg = "Incorrect argument format";
    protected static final String incorrectNumArgsMsg = "Incorrect number of arguments";
    protected static final String viewListOfAllCommands = "To view the list of all valid commands, simply type \"/help\".";

    protected static int MAX_NUMBER_OF_ARGS = 2;

    public abstract Command parse(String input) throws CommandParseException;

    protected String[] getParamsFromInput(String input) {
        return Arrays.stream(input.split(" "))
                .skip(1)
                .toArray(String[]::new);
    }

}
