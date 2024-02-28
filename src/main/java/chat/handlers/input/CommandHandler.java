package chat.handlers.input;

import chat.model.Commands;

import java.util.Arrays;

public class CommandHandler {

    String input;
    private static final String COMMAND_ERROR_MESSAGE = "command not found\nUse /help for more information";

    public String handleInput(String input) {
        return Arrays.stream(Commands.values()).anyMatch(command -> command.name().equals(input)) ? input : null;
    }

    public boolean isCommand(String input) {
        String commandNameProvided = input.replace("/", "").toUpperCase();
        return Arrays.stream(Commands.values())
                .anyMatch(command -> command.name().equals(commandNameProvided));
    }

    public String handleResult() {
        if (!isCommand(input))
            return input + " " + COMMAND_ERROR_MESSAGE;

        return input;
    }



}
