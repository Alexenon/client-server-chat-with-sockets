package chat.handlers.input.convertors;

import chat.handlers.input.InputType;
import chat.models.User;
import chat.ui.ChatLayout;

import javax.crypto.SecretKey;

public class InputConvertor {
    /**
     * FORMAT: {@code <receiver_username>: <message>} <br>
     * EXAMPLE: {@code Dan: This is a private message}
     */
    public static final String PRIVATE_MESSAGE_REGEX = "\\b(\\w+):\\s+(.*)";

    private final User user;
    private final SecretKey secretKey;
    private final ChatLayout chatLayout;

    public InputConvertor(ChatLayout chatLayout, User user, SecretKey secretKey) {
        this.chatLayout = chatLayout;
        this.user = user;
        this.secretKey = secretKey;
    }

    public Object convertIntoObject(String input, boolean shouldBeEncrypted) {
        Convertor convertor = getConvertor(input, shouldBeEncrypted);
        return convertor.getObjectFromInput(input);
    }

    private Convertor getConvertor(String input, boolean shouldBeEncrypted) {
        InputType inputType = getType(input);
        return switch (inputType) {
            case COMMAND -> new CommandConvertor(chatLayout);
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