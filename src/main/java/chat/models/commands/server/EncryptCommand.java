package chat.models.commands.server;

import chat.handlers.input.InputSendingHandler;
import chat.models.commands.Command;
import chat.models.commands.CommandType;
import chat.ui.ChatLayout;

/**
 * Options:
 * <li>/encrypt "message" - Encrypt just a message
 * <li>/encrypt ON - Turns ON encryption for messages
 * <li>/encrypt OFF - Turns OFF encryption for messages
 * <li>/encrypt STATUS - Displays if encryption is ON or OFF
 */
public class EncryptCommand extends Command {

    private static final String name = "encrypt";

    private final ChatLayout chatLayout;
    private final InputSendingHandler inputSendingHandler;
    private final String option;

    public EncryptCommand(ChatLayout chatLayout, InputSendingHandler inputSendingHandler) {
        this(chatLayout, inputSendingHandler, null);
    }

    public EncryptCommand(ChatLayout chatLayout, InputSendingHandler inputSendingHandler, String option) {
        super(name, CommandType.ENCRYPT);
        this.chatLayout = chatLayout;
        this.inputSendingHandler = inputSendingHandler;
        this.option = option;
    }

    @Override
    public void execute() {
        switch (option.toUpperCase()) {
            case "ON" -> {
                chatLayout.setEncryptCheckboxValue(true);
                chatLayout.updateChatArea("Encryption mode turned ON");
            }
            case "OFF" -> {
                chatLayout.setEncryptCheckboxValue(false);
                chatLayout.updateChatArea("Encryption mode turned OFF");
            }
            case "STATUS" -> {
                boolean encryptionMode = chatLayout.encryptCheckboxSelected();
                chatLayout.updateChatArea("Encryption mode: " + encryptionMode);
            }
            default -> inputSendingHandler.handleSendingObject(option, false);
        }
    }


}
