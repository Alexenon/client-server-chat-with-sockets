package chat.sever;

import chat.models.errors.StatusCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerResponse {
    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm";

    private final String responseToBeDisplayed;
    private final StatusCode statusCode;
    private final LocalDateTime dateTime;

    public ServerResponse(String responseToBeDisplayed, StatusCode statusCode) {
        this.responseToBeDisplayed = responseToBeDisplayed;
        this.statusCode = statusCode;
        this.dateTime = LocalDateTime.now();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeFormatted() {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER));
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
               "response='" + responseToBeDisplayed + '\'' +
               ", status=" + statusCode.getMessage() +
               ", statusCode=" + statusCode.getCode() +
               ", dateTime=" + getDateTimeFormatted() +
               '}';
    }

    public String getResponse() {
        return responseToBeDisplayed;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
