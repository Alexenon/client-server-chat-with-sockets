package chat.sever.handlers;

import chat.client.models.ClientRequest;
import chat.client.models.Message;
import chat.client.models.User;

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
            new ClientRequestResponseHandler(user).handle(clientRequest);
        } else if (receivedObject instanceof Message message) {
            new MessageResponseHandler().handle(message);
        } else {
            new DefaultResponseHandler().handle(receivedObject);
        }
    }

}
