package chat.handlers.response;

import chat.models.Message;
import chat.models.User;

public record ResponseHandlerFactory(User user) {

    public ResponseHandler createResponseHandler(Object object) {
        if (object instanceof Message message) {
            return new MessageHandlerImpl(message, user);
        } else if (object instanceof Error error) {
            return new ErrorHandlerImpl(error);
        }

        throw new RuntimeException("Couldn't process the response");
    }
}




