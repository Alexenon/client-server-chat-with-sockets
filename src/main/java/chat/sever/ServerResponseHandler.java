package chat.sever;

import chat.client.models.ClientRequest;
import chat.client.models.Message;
import chat.client.models.User;
import chat.utils.errors.UserNotFoundException;

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

    private Object getResponseFromClientRequest(Object object) {
        ClientRequest request = (ClientRequest) object;

        return switch (request.getRequestType()) {
            case VIEW_USER_INFO -> getUserInfo(request.getParams());
            case VIEW_MEMBERS -> getMembers();
            default -> throw new UnsupportedOperationException("Unexpected value: " + request.getRequestType());
        };
    }

    private Message getMembers() {
        StringBuilder sb = new StringBuilder("Members:\n");
        for (User u : ServerManager.getUsers()) {
            sb.append(" - ").append(u.getUsername()).append("\n");
        }
        String text = sb.substring(0, sb.length() - 1);
        return new Message(text);
    }

    private Message getUserInfo(String[] params) {
        String username = (params != null && params.length > 0) ? params[0] : null;
        String userInfoText = buildUserInfoText(username);
        return new Message(userInfoText);
    }

    private String buildUserInfoText(String username) {
        try {
            User userToBeDisplayed = (username != null) ? getUserByUsername(username) : user;
            return "Information " + userToBeDisplayed.getUsername() + ":\n" + userToBeDisplayed;
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

}
