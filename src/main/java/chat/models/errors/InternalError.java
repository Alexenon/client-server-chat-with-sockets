package chat.models.errors;

public class InternalError {
    private final StatusCode code;
    private final String errorMessage;

    public InternalError(StatusCode statusCode) {
        this.code = statusCode;
        this.errorMessage = statusCode.getMessage();
    }

    public InternalError(String errorMessage) {
        this.code = StatusCode.BAD_REQUEST;
        this.errorMessage = errorMessage;
    }

    public InternalError(StatusCode statusCode, String errorMessage) {
        this.code = statusCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getStatusErrorMessage() {
        return code.getMessage();
    }
}
