package chat.utils;

import java.util.stream.Stream;

public enum StatusCode {
    CONTINUE(100, "Continue"),
    // 200 - SUCCESS
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    // 400 - CLIENT
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_ALLOWED(402, "Not Allowed"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    // 500 - SERVER
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCode fromCode(int code) {
        return Stream.of(values())
                .filter(s -> s.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status code: " + code));
    }

    public static String getMessage(int code) {
        return fromCode(code).getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
