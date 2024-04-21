package chat.handlers.input.parsers;

import chat.handlers.input.InputSendingHandler;
import chat.models.commands.Command;
import chat.models.commands.server.EncryptCommand;
import chat.models.errors.CommandParseException;
import chat.ui.ChatLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptCommandParser extends CommandParser {
    /**
     * Pattern to match any quoted string or values ON|OFF|STATUS
     *
     * @see EncryptCommand
     */
    private static final Pattern OPTIONS_ALLOWED_PATTERN = Pattern.compile("(\"[^\"]*\")|(?i)(ON|OFF|STATUS)");

    private final ChatLayout chatLayout;
    private final InputSendingHandler inputSendingHandler;

    public EncryptCommandParser(ChatLayout chatLayout, InputSendingHandler inputSendingHandler) {
        this.chatLayout = chatLayout;
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public Command parse(String input) throws CommandParseException {
        String option = extractOption(input);
        return new EncryptCommand(chatLayout, inputSendingHandler, option);
    }

    /**
     * Extracts the option from the input string after sanitizing it.
     *
     * @param input the input string
     * @return the extracted option
     * @throws CommandParseException if the input does not match the expected format
     */
    private String extractOption(String input) throws CommandParseException {
        String sanitizedInput = sanitizeInput(input);
        Matcher matcher = OPTIONS_ALLOWED_PATTERN.matcher(sanitizedInput);

        if (!matcher.find()) {
            throw new CommandParseException(
                    String.format("Invalid command option \"%s\". To view the list of encrypt options, type \"/help encrypt\".", sanitizedInput)
            );
        }

        String quotedString = matcher.group(1);
        String option = matcher.group(2);

        return getExtractedOption(quotedString, option);
    }

    /**
     * Removes the command prefix and trims the input string.
     */
    private String sanitizeInput(String input) {
        return input.replace("/encrypt", "").trim();
    }

    /**
     * Extracts and returns the appropriate option from the matched groups.
     *
     * @param quotedString the matched quoted string
     * @param option       the matched option
     * @return the extracted option
     * @throws CommandParseException if both quotedString and option are null
     */
    private String getExtractedOption(String quotedString, String option) throws CommandParseException {
        if (quotedString != null) {
            return extractQuotedString(quotedString);
        } else if (option != null) {
            return option;
        } else {
            throw new CommandParseException("Invalid input format");
        }
    }

    /**
     * Extracts and returns the content of the quoted string.
     *
     * @param quotedString the matched quoted string
     * @return the content of the quoted string
     */
    private String extractQuotedString(String quotedString) {
        return quotedString.substring(1, quotedString.length() - 1);
    }
}
