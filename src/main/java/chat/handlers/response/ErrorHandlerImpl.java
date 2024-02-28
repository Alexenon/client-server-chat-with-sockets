package chat.handlers.response;

public record ErrorHandlerImpl(Error error) implements ResponseHandler {
    @Override
    public String handleResult() {
        return error.toString();
    }
}