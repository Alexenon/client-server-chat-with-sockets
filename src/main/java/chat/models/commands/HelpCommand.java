package chat.models.commands;

import chat.models.Commands;

import java.util.Arrays;
import java.util.Optional;

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
        System.out.println("Executing \"/help\" command");
    }

    @Override
    public boolean isValid() {
        String[] args = input.split(" ");

        if (args.length > 2) return false;
        if (!args[0].equals("/help")) return false;

        return Arrays.stream(Commands.values())
                .map(c -> c.toString().toLowerCase())
                .anyMatch(s -> s.equals(commandForHelp));
    }

    @Override
    public Optional<String> result() {
        return Optional.of(getResultPerCommand(commandForHelp));
    }

    private String getResultPerCommand(String command) {
        return switch (command) {
            case "" -> """
                    List of available commands:
                        /help - Display this help message.
                        /encrypt [message] - Encrypt a message
                        /user [username] - View information about a specific user.
                        /private [username] - Start a private conversation with a user.
                        /exit - Exit the chat.
                        
                    Options:
                        /help [command] - Display information about a specific command.
                    """;
            case "encrypt" -> "MISSING";
            case "exit" -> "MISSING TOO";
            default -> null;
        };
    }
}
