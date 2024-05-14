package chat.client.models.commands.client;

import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.client.ui.ChatLayout;

public class ClearCommand extends Command {
    private final ChatLayout chatLayout;

    public ClearCommand(ChatLayout chatLayout) {
        super(CommandType.CLEAR);
        this.chatLayout = chatLayout;
    }

    @Override
    public void execute() {
        chatLayout.clearChat();
    }


}
