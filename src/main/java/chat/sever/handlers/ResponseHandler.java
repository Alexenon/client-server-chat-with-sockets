package chat.sever.handlers;

import chat.sever.ServerManager;

public abstract class ResponseHandler<T> {

    public void handle(T object) {
        ServerManager.broadcast(object);
    }

}
