package chat.client.handlers.input.convertors;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.client.handlers.parsers.*;
import chat.utils.errors.CommandParseException;
import chat.utils.errors.InternalError;
import chat.utils.errors.InvalidCommandException;
import chat.utils.StatusCode;
import chat.client.ui.ChatLayout;

import java.util.stream.Stream;

public class CommandConvertor implements Convertor {
    private static final String ERROR_MESSAGE = "Invalid command: \"%s\". To view the list of all valid commands, simply type \"/help\".";

    private final ChatLayout chatLayout;
    private final InputSendingHandler inputSendingHandler;

    public CommandConvertor(ChatLayout chatLayout, InputSendingHandler inputSendingHandler) {
        this.chatLayout = chatLayout;
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public Object getObjectFromInput(String input) {
        try {
            return getCommandFromInput(input);
        } catch (InvalidCommandException | CommandParseException e) {
            return new InternalError(StatusCode.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    public Command getCommandFromInput(String input) throws InvalidCommandException, CommandParseException {
        if (isInvalidCommand(input))
            throw new InvalidCommandException(ERROR_MESSAGE.formatted(input));

        return getCommandParser(input).parse(input);
    }

    public CommandParser getCommandParser(String input) throws InvalidCommandException {
        String commandName = extractCommandName(input);
        return switch (commandName) {
            case "/help" -> new HelpCommandParser(chatLayout);
            case "/exit" -> new ExitCommandParser(chatLayout);
            case "/members" -> new MembersCommandParser(inputSendingHandler);
            case "/encrypt" -> new EncryptCommandParser(chatLayout, inputSendingHandler);
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