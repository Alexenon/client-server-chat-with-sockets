package chat.sever.models;

import chat.client.models.User;
import chat.utils.StatusCode;

import java.io.Serial;
import java.io.Serializable;

public class CommandResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 7325273500065177433L;

    private final User receiver;
    private final StatusCode statusCode;
    private final String displayMessage;

    public CommandResponse(String displayMessage, StatusCode statusCode, User receiver) {
        this.receiver = receiver;
        this.statusCode = statusCode;
        this.displayMessage = displayMessage;
    }

    public User getReceiver() {
        return receiver;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    @Override
    public String toString() {
        return "CommandResponse{" +
               "receiver=" + receiver +
               ", statusCode=" + statusCode +
               ", displayMessage='" + displayMessage + '\'' +
               '}';
    }
}
