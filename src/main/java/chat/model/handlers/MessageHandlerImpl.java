package chat.model.handlers;

import chat.model.Message;

public record MessageHandlerImpl(Message message) implements ResponseHandler {
    @Override
    public String handleResult() {
        return message.toString();
    }
}