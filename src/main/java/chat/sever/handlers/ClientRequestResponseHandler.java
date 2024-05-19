package chat.sever.handlers;

import chat.client.models.ClientRequest;
import chat.client.models.User;
import chat.sever.models.CommandResponse;
import chat.sever.ServerManager;
import chat.utils.StatusCode;
import chat.utils.errors.UserNotFoundException;

import static chat.utils.ServerConfiguration.DATE_TIME_FORMATTER;

public class ClientRequestResponseHandler extends ResponseHandler<ClientRequest> {
    private final User user;

    public ClientRequestResponseHandler(User user) {
        this.user = user;
    }

    @Override
    public void handle(ClientRequest clientRequest) {
        Object response = getResponseFromClientRequest(clientRequest);
        ServerManager.broadcast(response, user);
    }

    private Object getResponseFromClientRequest(ClientRequest request) {
        return switch (request.getRequestType()) {
            case VIEW_USER_INFO -> viewUserInformation(request.getParams());
            case VIEW_CONNECTED_USERS -> viewListOfConnectedUsers();
        };
    }

    private CommandResponse viewListOfConnectedUsers() {
        StringBuilder sb = new StringBuilder("Connected users:\n");
        for (User u : ServerManager.getUsers()) {
            sb.append(" - ").append(u.getUsername()).append("\n");
        }
        String text = sb.substring(0, sb.length() - 1);
        return new CommandResponse(text, StatusCode.ACCEPTED, user);
    }

    private CommandResponse viewUserInformation(String[] params) {
        String username = (params != null && params.length > 0) ? params[0] : null;
        try {
            User userToBeDisplayed = (username != null) ? getUserByUsername(username) : user;
            String text = "Information %s:\n%s".formatted(userToBeDisplayed.getUsername(), userInfo(userToBeDisplayed));
            return new CommandResponse(text, StatusCode.ACCEPTED, user);
        } catch (UserNotFoundException e) {
            return new CommandResponse(e.getMessage(), StatusCode.BAD_REQUEST, user);
        }
    }

    private User getUserByUsername(String username) throws UserNotFoundException {
        return ServerManager.getUsers()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Couldn't find user with username: \"%s\"".formatted(username)));
    }

    private String userInfo(User user) {
        return """
                User{
                    username = "%s"
                    dateCreated = %s
                    lastLoginedTime = %s
                    role = DEFAULT
                }
                """.formatted(user.getUsername(),
                DATE_TIME_FORMATTER.format(user.getDateCreated()),
                DATE_TIME_FORMATTER.format(user.getLastLoginedTime()));
    }
}
