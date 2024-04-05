package chat.models.commands.executors;

import chat.models.commands.Command;
import chat.models.commands.ExitCommand;
import chat.ui.ChatLayout;

public class ExitCommandExecutor extends CommandExecutor<ExitCommand> {

    private final ChatLayout chatLayout;

    public ExitCommandExecutor(ChatLayout chatLayout) {
        super(chatLayout);
        this.chatLayout = chatLayout;
    }

    @Override
    public void execute(ExitCommand command) {
        if (command.isValid()) {
            command.execute();
            chatLayout.closeWindow();
        } else {
            chatLayout.updateChatArea(command.getResult());
        }
    }
}
