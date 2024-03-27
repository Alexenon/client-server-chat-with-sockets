package chat.models.temp;

import chat.models.Commands;
import chat.models.commands.Command;
import chat.models.commands.ExitCommand;
import chat.models.commands.HelpCommand;
import chat.models.commands.InvalidCommandException;
import chat.models.errors.InternalError;
import chat.models.errors.StatusCode;

import java.util.Arrays;

public class CommandConvertor implements Convertor {

    @Override
    public Object getObjectFromInput(String input) {
        try {
            Command command = getCommandFromInput(input);
            command.execute();
            return command;
        } catch (InvalidCommandException e) {
            e.printStackTrace();
            return new InternalError(StatusCode.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    public Command getCommandFromInput(String input) throws InvalidCommandException {
        if (isCommandValid(input))
            throw new InvalidCommandException("Invalid command: '" + input + "'");

        String commandName = extractCommandName(input);
        return switch (commandName) {
            case "/help" -> new HelpCommand(input);
            case "/exit" -> new ExitCommand(input);
            default -> throw new InvalidCommandException("Invalid command: '" + commandName + "'");
        };
    }

    public boolean isCommandValid(String input) {
        if (!input.startsWith("/")) return false;

        String commandName = extractCommandName(input);
        return Arrays.stream(Commands.values())
                .map(command -> command.name().toLowerCase())
                .anyMatch(commandName::equals);
    }

    public String extractCommandName(String input) {
        return input.split(" ")[0].toLowerCase();
    }

}