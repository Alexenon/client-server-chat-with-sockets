package chat.models.errors;

public class InvalidCommandException extends Exception {

    public InvalidCommandException(String message) {
        super(message);
    }
}
