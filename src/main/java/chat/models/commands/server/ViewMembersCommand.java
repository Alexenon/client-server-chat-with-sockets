package chat.models.commands.server;

import chat.handlers.input.InputSendingHandler;
import chat.models.User;
import chat.models.commands.Command;
import chat.models.commands.CommandType;
import chat.sever.ClientRequest;
import chat.sever.RequestType;

public class ViewMembersCommand extends Command {
    private static final String name = "members";

    private final InputSendingHandler inputSendingHandler;

    public ViewMembersCommand(InputSendingHandler inputSendingHandler) {
        super(name, CommandType.VIEW_MEMBERS);
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public void execute() {
        User receiver = inputSendingHandler.getUser();
        ClientRequest request = new ClientRequest(RequestType.VIEW_MEMBERS, receiver);
        inputSendingHandler.sendToServer(request);
    }
}
