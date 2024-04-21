package chat.models.commands;

import chat.ui.ChatLayout;

import java.util.Objects;

public class HelpCommand extends Command {

    private final static String name = "help";
    private final ChatLayout chatLayout;
    private final String nameCommandForHelp;

    public HelpCommand(ChatLayout chatLayout) {
        this(chatLayout, null);
    }

    public HelpCommand(ChatLayout chatLayout, String nameCommandForHelp) {
        super(name, CommandType.HELP);
        this.chatLayout = chatLayout;
        this.nameCommandForHelp = Objects.requireNonNullElse(nameCommandForHelp, "");
    }

    @Override
    public void execute() {
        String helpCommandResult = getHelpForCommand(nameCommandForHelp);
        chatLayout.updateChatArea(helpCommandResult);
    }

    private String getHelpForCommand(String name) {
        return switch (name) {
            case "" -> """
                    List of all available commands:
                        /help               - Display help message.
                        /encrypt [message]  - Encrypt a message
                        /user    [username] - View information about a specific user.
                        /private [username] - Start a private conversation with a user.
                        /exit               - Exit the chat.
                        
                    Options:
                        /help [command name] - Display information about a specific command.
                    """;
            case "encrypt" -> """
                    Encrypts a message, or a series of messages.
                                        
                    Options:
                        /encrypt [message] - Encrypt just a particular message
                        /encrypt [on] - Turns ON encryption for messages
                        /encrypt [off] - Turns OFF encryption for messages
                    """;
            case "exit" -> "Disconnects from the server";
            default -> null;
        };
    }

}
