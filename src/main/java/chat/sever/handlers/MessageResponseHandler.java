package chat.sever.handlers;

import chat.client.models.Message;
import chat.sever.ServerManager;

public class MessageResponseHandler extends ResponseHandler<Message> {

    @Override
    public void handle(Message message) {
        if (message.getReceiver() != null) {
            ServerManager.broadcast(message, message.getReceiver());
        } else {
            ServerManager.broadcast(message);
        }
    }
}
