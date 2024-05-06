package chat.client.models.commands.server;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.models.User;
import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.client.models.ClientRequest;
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
