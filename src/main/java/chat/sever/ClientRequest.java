package chat.sever;

import chat.models.User;

import java.io.Serial;
import java.io.Serializable;

public class ClientRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 120127867010854L;

    private final RequestType requestType;
    private final User receiver;

    public ClientRequest(RequestType requestType, User receiver) {
        this.requestType = requestType;
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "ClientRequest{requestType=%s, receiver=%s}".formatted(requestType, receiver);
    }

    public User getReceiver() {
        return receiver;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
