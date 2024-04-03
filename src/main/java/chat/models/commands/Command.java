package chat.models.commands;

public interface Command {

    void execute() throws InvalidCommandException;

    boolean isValid() throws InvalidCommandException;

    String getResult();

    default String getErrorMessage() {
        return "Invalid command. To view the list of all valid commands, simply type \"/help\".";
    };
}
