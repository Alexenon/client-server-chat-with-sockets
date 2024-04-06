package chat.models.commands.executors;

import chat.models.commands.Command;

@FunctionalInterface
public interface CommandExecutor {

    void execute(Command command);

}
