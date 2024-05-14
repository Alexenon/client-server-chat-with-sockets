package chat.utils.errors;

import chat.utils.StatusCode;

public class InternalError {
    private final StatusCode statusCode;
    private final String errorMessage;

    public InternalError(StatusCode statusCode) {
        this.statusCode = statusCode;
        this.errorMessage = statusCode.getMessage();
    }

    public InternalError(String errorMessage) {
        this.statusCode = StatusCode.BAD_REQUEST;
        this.errorMessage = errorMessage;
    }

    public InternalError(StatusCode statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "InternalError{statusCode=%s, errorMessage='%s'}".formatted(statusCode, errorMessage);
    }
}
