package chat.handlers.input.command;

import chat.models.Commands;
import chat.models.commands.Command;
import chat.models.commands.ExitCommand;
import chat.models.commands.HelpCommand;
import chat.models.commands.InvalidCommandException;

import java.util.Arrays;

public class CommandInputHandler {

    private final String input;
    private final String commandName;

    public CommandInputHandler(String input) {
        this.input = input;
        this.commandName = extractCommandName();
    }

    public Command getCommand() throws InvalidCommandException {
        if (isCommandValid(input))
            throw new InvalidCommandException("Invalid command: '" + input + "'");

        return switch (commandName) {
            case "/help" -> new HelpCommand(input);
            case "/exit" -> new ExitCommand(input);
            default -> throw new InvalidCommandException("Invalid command: '" + commandName + "'");
        };
    }

    public boolean isCommandValid(String input) {
        if (!input.startsWith("/")) return false;

        return Arrays.stream(Commands.values())
                .map(command -> command.name().toLowerCase())
                .anyMatch(this.commandName::equals);
    }

    public String extractCommandName() {
        return input.split(" ")[0].toLowerCase();
    }

}
