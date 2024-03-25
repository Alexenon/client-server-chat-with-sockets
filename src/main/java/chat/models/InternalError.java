package chat.models;


public class InternalError {
    int code;
    String message;

    public InternalError(String errorMessage) {
        this.code = 200;
        this.message = errorMessage;
    }

    public InternalError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
