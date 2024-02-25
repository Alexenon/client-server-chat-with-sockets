package chat.model.handlers;

import chat.model.Message;

public class ResponseHandlerFactory {
    public static ResponseHandler createResponseHandler(Object object) {
        if (object instanceof Message) {
            return new MessageHandlerImpl((Message) object);
        } else if (object instanceof Error) {
            return new ErrorHandlerImpl((Error) object);
        }
        return null;
    }
}




