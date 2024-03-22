package chat.models.commands;

public interface Command {

    void execute();

    boolean isValid() throws InvalidCommandException;

    String getResult();

    default String getErrorMessage() {
        return "Invalid command. Type /help for a list of all commands.";
    };
}
