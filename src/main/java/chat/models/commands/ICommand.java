package chat.models.commands;

import java.util.Optional;

public interface ICommand {

    void execute();

    boolean isValid();

    Optional<String> result();

    default String getErrorMessage() {
        return "Invalid command. Type /help for a list of all commands.";
    };
}
