package chat.models.commands.executors;

import chat.models.commands.Command;
import chat.ui.ChatLayout;

public abstract class CommandExecutor<T extends Command> {

    private final ChatLayout chatLayout;

    public CommandExecutor(ChatLayout chatLayout) {
        this.chatLayout = chatLayout;
    }

    public abstract void execute(T command);

}
