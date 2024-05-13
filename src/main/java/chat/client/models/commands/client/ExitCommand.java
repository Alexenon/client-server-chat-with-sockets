package chat.client.models.commands.client;

import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.client.ui.ChatLayout;

public class ExitCommand extends Command {
    private final ChatLayout chatLayout;

    public ExitCommand(ChatLayout chatLayout) {
        super(CommandType.EXIT);
        this.chatLayout = chatLayout;
    }

    @Override
    public void execute() {
        chatLayout.closeWindow();
    }

}
