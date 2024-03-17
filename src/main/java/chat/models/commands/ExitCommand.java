package chat.models.commands;

import chat.models.commands.Command;

import java.util.Optional;

public class ExitCommand implements Command {
    String input;
    public ExitCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Optional<String> result() {
        return Optional.empty();
    }
}
