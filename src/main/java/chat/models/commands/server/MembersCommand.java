package chat.models.commands.server;

import chat.handlers.input.InputSendingHandler;
import chat.models.User;
import chat.models.commands.Command;
import chat.models.commands.CommandType;
import chat.sever.ClientRequest;
import chat.sever.RequestType;

public class MembersCommand extends Command {
    private static final String name = "members";

    private final InputSendingHandler inputSendingHandler;

    public MembersCommand(InputSendingHandler inputSendingHandler) {
        super(name, CommandType.MEMBERS);
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public void execute() {
        User user = inputSendingHandler.getUser();
        ClientRequest request = new ClientRequest(user, RequestType.VIEW_MEMBERS);
        inputSendingHandler.sendToServer(request);
    }
}
