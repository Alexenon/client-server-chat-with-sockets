package chat.client.models.commands.server;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.client.ui.ChatLayout;

import java.awt.*;

/**
 * Options:
 * <li>/encrypt "message" - Encrypt just a message
 * <li>/encrypt ON - Turns ON encryption for messages
 * <li>/encrypt OFF - Turns OFF encryption for messages
 * <li>/encrypt STATUS - Displays if encryption is ON or OFF
 */
public class EncryptCommand extends Command {
    private final ChatLayout chatLayout;
    private final InputSendingHandler inputSendingHandler;
    private final String option;

    public EncryptCommand(ChatLayout chatLayout, InputSendingHandler inputSendingHandler, String option) {
        super(CommandType.ENCRYPT);
        this.chatLayout = chatLayout;
        this.inputSendingHandler = inputSendingHandler;
        this.option = option;
    }

    @Override
    public void execute() {
        switch (option.toUpperCase()) {
            case "ON" -> {
                chatLayout.setEncryptCheckboxValue(true);
                chatLayout.updateChatArea("Encryption mode turned ON", Color.GREEN);
            }
            case "OFF" -> {
                chatLayout.setEncryptCheckboxValue(false);
                chatLayout.updateChatArea("Encryption mode turned OFF", Color.GREEN);
            }
            case "STATUS" -> {
                String encryptionMode = String.valueOf(chatLayout.encryptCheckboxSelected()).toUpperCase();
                chatLayout.updateChatArea("Encryption mode: " + encryptionMode, Color.GREEN);
            }
            default -> inputSendingHandler.handleSendingObject(option, false);
        }
    }


}
