package chat.models.commands.executors;

import chat.models.commands.Command;

@FunctionalInterface
public interface ICommandExecutor {

    void execute(Command command);

}
