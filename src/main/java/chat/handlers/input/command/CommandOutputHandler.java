package chat.handlers.input.command;

import chat.models.Command;
import chat.models.Message;

public class CommandOutputHandler {

    private static final String USE_HELP = "Use /help for more information";
    private static final String UNKNOWN_COMMAND_ERROR_MESSAGE = "command not found\nUse /help for more information";

    private final Command command;

    public CommandOutputHandler(Command command) {
        this.command = command;
    }

    public Object getResponse() {
        return command.getText().startsWith("/encrypt")
                ? getEncryptedMessage()
                : getInformation();
    }

    private Message getEncryptedMessage() {
        return null;
    }

    private String getInformation() {
        return "";
    }

}
