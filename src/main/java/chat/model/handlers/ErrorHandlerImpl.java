package chat.model.handlers;

public record ErrorHandlerImpl(Error error) implements ResponseHandler {
    @Override
    public String handleResult() {
        return error.toString();
    }
}