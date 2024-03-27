package chat.models.errors;

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
}
