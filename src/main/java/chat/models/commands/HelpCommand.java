package chat.models.commands;

import chat.models.Commands;

import java.util.Arrays;

public class HelpCommand implements Command {
    private final String input;
    private final String commandForHelp;

    public HelpCommand(String input) {
        this.input = input;
        this.commandForHelp = extractCommandForHelp();
    }

    private String extractCommandForHelp() {
        String[] splits = input.split(" ");
        return splits.length > 1 ? splits[1] : "";
    }

    @Override
    public void execute() {
        System.out.println("Executing \"" + input + "\" command");
    }

    @Override
    public boolean isValid() throws InvalidCommandException {
        String[] args = input.split(" ");

        if (args.length > 2)
            throw new InvalidCommandException("Too many arguments for command \"/help\" found");

        return isCommandForHelpValid();
    }

    private boolean isCommandForHelpValid() {
        if (commandForHelp.isEmpty()) return true;

        return Arrays.stream(Commands.values())
                .map(c -> c.toString().toLowerCase())
                .anyMatch(s -> s.equals(commandForHelp));
    }

    public String getResult() {
        return getResultPerCommand(commandForHelp) == null
                ? getErrorMessage()
                : getResultPerCommand(commandForHelp);
    }

    private String getResultPerCommand(String command) {
        return switch (command) {
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
                        /encrypt [on] - Turns ON encryption for next messages
                        /encrypt [off] - Turns OFF encryption for next messages
                    """;
            case "exit" -> "Disconnects from the server";
            default -> null;
        };
    }
}
