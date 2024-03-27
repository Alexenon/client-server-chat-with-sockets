package chat.models.temp;

import chat.models.User;

import javax.crypto.SecretKey;

public class InputConvertor {
    /**
     * FORMAT: {@code <receiver_username>: <message>} <br>
     * EXAMPLE: {@code Dan: This is a private message}
     */
    public static final String PRIVATE_MESSAGE_REGEX = "\\b(\\w+):\\s+(.*)";

    private final User user;
    private final SecretKey secretKey;
    private final boolean shouldBeEncrypted;

    public InputConvertor(User user, SecretKey secretKey, boolean shouldBeEncrypted) {
        this.user = user;
        this.secretKey = secretKey;
        this.shouldBeEncrypted = shouldBeEncrypted;
    }

    public Object convertIntoObject(String input) {
        Convertor convertor = getConvertor(input);
        return convertor.getObjectFromInput(input);
    }

    private Convertor getConvertor(String input) {
        InputType inputType = getType(input);
        return switch (inputType) {
            case COMMAND -> new CommandConvertor();
            case PUBLIC_MESSAGE -> new PublicMessageConvertor(user, secretKey, shouldBeEncrypted);
            case PRIVATE_MESSAGE -> new PrivateMessageConvertor(user, secretKey, shouldBeEncrypted);
        };
    }

    public InputType getType(String input) {
        if (isCommand(input)) return InputType.COMMAND;
        if (isPrivateMessage(input)) return InputType.PRIVATE_MESSAGE;
        return InputType.PUBLIC_MESSAGE;
    }

    public boolean isCommand(String input) {
        return input.startsWith("/");
    }

    public boolean isPrivateMessage(String input) {
        return input.matches(PRIVATE_MESSAGE_REGEX);
    }

}