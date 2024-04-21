package chat.sever;

import chat.models.User;

import java.io.Serial;
import java.io.Serializable;

public class ClientRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 120127867010854L;

    private final User receiver;
    private final RequestType requestType;

    public ClientRequest(User receiver, RequestType requestType) {
        this.receiver = receiver;
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "ClientRequest{requestType=%s, receiver=%s}".formatted(requestType, receiver);
    }
}
