package chat.handlers.input;

import chat.models.Command;
import chat.models.Commands;

import java.util.Arrays;
import java.util.Optional;

public class CommandInputHandler {

    private final String input;

    public CommandInputHandler(String input) {
        this.input = input;
    }

    public String isCommand() {
        return Arrays.stream(Commands.values()).anyMatch(command -> command.name().equals(input)) ? input : null;
    }

    public boolean isCommandValid(String input) {
        String commandNameProvided = input.replace("/", "").toUpperCase();
        return Arrays.stream(Commands.values())
                .anyMatch(command -> command.name().equals(commandNameProvided));
    }

    public Optional<Command> getCommand() {
        return Optional.empty();
    }


}
