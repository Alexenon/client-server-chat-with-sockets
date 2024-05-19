package chat.client.models;

import chat.sever.models.RequestType;

import java.io.Serial;
import java.io.Serializable;

public class ClientRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 120127867010854L;

    private final RequestType requestType;
    private final User receiver;
    private final String[] params;

    public ClientRequest(RequestType requestType, User receiver) {
        this(requestType, receiver, new String[]{});
    }

    public ClientRequest(RequestType requestType, User receiver, String[] params) {
        this.requestType = requestType;
        this.receiver = receiver;
        this.params = params;
    }

    public User getReceiver() {
        return receiver;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String[] getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "ClientRequest{requestType=%s, receiver=%s}".formatted(requestType, receiver);
    }
}
