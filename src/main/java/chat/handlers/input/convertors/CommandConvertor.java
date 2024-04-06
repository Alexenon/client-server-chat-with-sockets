package chat.handlers.input.convertors;

import chat.models.commands.Command;
import chat.models.commands.CommandType;
import chat.models.commands.ExitCommand;
import chat.models.commands.HelpCommand;
import chat.models.errors.InternalError;
import chat.models.errors.InvalidCommandException;
import chat.models.errors.StatusCode;
import chat.ui.ChatLayout;

import java.util.stream.Stream;

public class CommandConvertor implements Convertor {
    private static final String ERROR_MESSAGE = "Invalid command: \"%s\". To view the list of all valid commands, simply type \"/help\".";

    private final ChatLayout chatLayout;

    public CommandConvertor(ChatLayout chatLayout) {
        this.chatLayout = chatLayout;
    }

    @Override
    public Object getObjectFromInput(String input) {
        try {
            return getCommandFromInput(input);
        } catch (InvalidCommandException e) {
            return new InternalError(StatusCode.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    public Command getCommandFromInput(String input) throws InvalidCommandException {
        if (isInvalidCommand(input))
            throw new InvalidCommandException(ERROR_MESSAGE.formatted(input));

        String commandName = extractCommandName(input);
        return switch (commandName) {
            case "/help" -> new HelpCommand(null);
            case "/exit" -> new ExitCommand(null);
            default -> throw new InvalidCommandException(ERROR_MESSAGE.formatted(commandName));
        };
    }

    public boolean isInvalidCommand(String input) {
        if (!input.startsWith("/"))
            return true;

        String commandName = extractCommandName(input);
        return Stream.of(CommandType.values())
                .map(Enum::name)
                .map(s -> "/" + s.toLowerCase())
                .noneMatch(commandName::equals);
    }

    public String extractCommandName(String input) {
        return input.split(" ")[0].toLowerCase();
    }

}