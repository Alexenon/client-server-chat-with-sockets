package chat.sever;

import chat.client.models.ClientRequest;
import chat.client.models.Message;
import chat.client.models.User;
import chat.utils.errors.UserNotFoundException;

import static chat.utils.ServerConfiguration.DATE_TIME_FORMATTER;

public class ServerResponseHandler {
    private final User user;

    public ServerResponseHandler(User user) {
        this.user = user;
    }

    /**
     * Handles the input received from the user<br>
     * If the input its type of {@link ClientRequest} then the result is sent just to the author of the request,
     * in any other cases the response is sent to all the users
     */
    public void handleResponse(Object receivedObject) {
        if (receivedObject instanceof ClientRequest clientRequest) {
            Object response = getResponseFromClientRequest(clientRequest);
            ServerManager.broadcast(response, user);
        } else {
            ServerManager.broadcast(receivedObject);
        }
    }

    private Object getResponseFromClientRequest(ClientRequest request) {
        return switch (request.getRequestType()) {
            case VIEW_USER_INFO -> viewUserInformation(request.getParams());
            case VIEW_CONNECTED_USERS -> viewListOfConnectedUsers();
            default -> throw new UnsupportedOperationException("Unexpected value: " + request.getRequestType());
        };
    }

    private Message viewListOfConnectedUsers() {
        StringBuilder sb = new StringBuilder("Connected users:\n");
        for (User u : ServerManager.getUsers()) {
            sb.append(" - ").append(u.getUsername()).append("\n");
        }
        String text = sb.substring(0, sb.length() - 1);
        return new Message(text);
    }

    private Message viewUserInformation(String[] params) {
        String username = (params != null && params.length > 0) ? params[0] : null;
        String userInfoText = buildUserInfoText(username);
        return new Message(userInfoText);
    }

    private String buildUserInfoText(String username) {
        try {
            User userToBeDisplayed = (username != null) ? getUserByUsername(username) : user;
            return "Information " + userToBeDisplayed.getUsername() + ":\n" + userInfo(userToBeDisplayed);
        } catch (UserNotFoundException e) {
            return e.getMessage();
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
