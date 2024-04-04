package chat.models.commands.executors;

import chat.models.commands.HelpCommand;
import chat.models.commands.InvalidCommandException;
import chat.ui.ChatLayout;

public class HelpCommandExecutor extends CommandExecutor<HelpCommand> {

    private final ChatLayout chatLayout;

    public HelpCommandExecutor(ChatLayout chatLayout) {
        super(chatLayout);
        this.chatLayout = chatLayout;
    }

    @Override
    public void execute(HelpCommand command) {
        try {
            command.execute();
            chatLayout.updateChatArea(command.getResult());
        } catch (InvalidCommandException e) {
            String localizedMessage = e.getLocalizedMessage();
            System.out.println("ERROR: " + localizedMessage);
            chatLayout.updateChatArea(localizedMessage);
        }
    }

}

