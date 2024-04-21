package chat.models.commands;

import chat.ui.ChatLayout;

public class ExitCommand extends Command {

    private final static String name = "exit";
    private final ChatLayout chatLayout;

    public ExitCommand(ChatLayout chatLayout) {
        super(name, CommandType.EXIT);
        this.chatLayout = chatLayout;
    }

    @Override
    public void execute() {
        chatLayout.closeWindow();
    }

}
