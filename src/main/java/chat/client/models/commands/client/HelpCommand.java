package chat.client.models.commands.client;

import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.client.ui.ChatLayout;

import java.util.Objects;

public class HelpCommand extends Command {
    private final ChatLayout chatLayout;
    private final String nameCommandForHelp;

    public HelpCommand(ChatLayout chatLayout, String nameCommandForHelp) {
        super(CommandType.HELP);
        this.chatLayout = chatLayout;
        this.nameCommandForHelp = Objects.requireNonNullElse(nameCommandForHelp, "");
    }

    @Override
    public void execute() {
        String helpCommandResult = getHelpForCommand(nameCommandForHelp);
        chatLayout.displayCommand(helpCommandResult);
    }

    private String getHelpForCommand(String name) {
        return switch (name) {
            case "" -> """
                    List of all available commands:
                        /help               - Display help message.
                        /encrypt [message]  - Encrypt a message
                        /user    [username] - View information about a specific user.
                        /users              - Displays all the active users connected to the chat.
                        /exit               - Exit the chat.
                        
                    Options:
                        /help [command name] - Display information about a specific command.
                    """;
            case "encrypt" -> """
                    Encrypts a message, or a series of messages.
                                        
                    Options:
                        /encrypt [message] - Encrypt just a particular message
                        /encrypt ON - Turns ON encryption for messages
                        /encrypt OFF - Turns OFF encryption for messages
                        /encrypt STATUS - Check the status of the chat encryption
                    """;
            case "users" -> "Displays all the active users connected to the chat";
            case "exit" -> "Disconnects from the server";
            default -> null;
        };
    }

}
