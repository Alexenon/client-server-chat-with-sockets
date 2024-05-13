package chat.client.models.commands.server;

import chat.client.handlers.input.InputSendingHandler;
import chat.client.models.ClientRequest;
import chat.client.models.User;
import chat.client.models.commands.Command;
import chat.client.models.commands.CommandType;
import chat.sever.RequestType;

public class ViewUserInfoCommand extends Command {
    private static final String name = "/info";

    private final String username;
    private final InputSendingHandler inputSendingHandler;

    /**
     * @param username required for finding information about user with same username
     */
    public ViewUserInfoCommand(InputSendingHandler inputSendingHandler, String username) {
        super(name, CommandType.VIEW_USER_INFO);
        this.username = username;
        this.inputSendingHandler = inputSendingHandler;
    }

    @Override
    public void execute() {
        User receiver = inputSendingHandler.getUser();
        String[] params =getParamsForRequest();
        ClientRequest request = new ClientRequest(RequestType.VIEW_USER_INFO, receiver, params);
        inputSendingHandler.sendToServer(request);
    }

    private String[] getParamsForRequest() {
        return username == null || username.isEmpty() || username.isBlank()
                ? null
                : new String[]{username};
    }
}