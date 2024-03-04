package chat.models.commands;

import java.util.Optional;

public interface ICommand {

    void execute();

    boolean isValid();

    Optional<String> result();

}
