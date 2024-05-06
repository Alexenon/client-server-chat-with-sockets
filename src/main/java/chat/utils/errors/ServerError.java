package chat.utils.errors;

import chat.utils.StatusCode;

import java.io.Serial;
import java.io.Serializable;

public class ServerError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1730205511362758432L;

    private final String errorMessage;
    private final StatusCode statusCode;

    public ServerError(StatusCode statusCode) {
        this.errorMessage = statusCode.getMessage();
        this.statusCode = statusCode;
    }

    public ServerError(String errorMessage) {
        this.errorMessage = errorMessage;
        this.statusCode = StatusCode.BAD_REQUEST;
    }

    public ServerError(String errorMessage, StatusCode statusCode) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "ServerError{cause='%s', statusCode='%s'}"
                .formatted(getErrorMessage(), getStatusCode().getMessage());
    }
}
