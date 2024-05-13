package chat.client.models.commands.server;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.models.User;
import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.client.models.ClientRequest;
import chat.sever.RequestType;

public class ViewConnectedUsersCommand extends Command {
    private final InputSendingHandler inputSendingHandler;

    public ViewConnectedUsersCommand(InputSendingHandler inputSendingHandler) {
        super(CommandType.VIEW_CONNECTED_USERS);
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public void execute() {
        User receiver = inputSendingHandler.getUser();
        ClientRequest request = new ClientRequest(RequestType.VIEW_CONNECTED_USERS, receiver);
        inputSendingHandler.sendToServer(request);
    }
}
