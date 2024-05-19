package chat.sever.models;

import chat.utils.ServerConfiguration;
import chat.utils.StatusCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ServerResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3218722181592127601L;

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


    @Override
    public String toString() {
        return "ServerResponse{" +
               "response='" + responseToBeDisplayed + '\'' +
               ", status=" + statusCode.getMessage() +
               ", statusCode=" + statusCode.getCode() +
               ", dateTime=" + dateTime.format(ServerConfiguration.DATE_TIME_FORMATTER) +
               '}';
    }

    public String getResponse() {
        return responseToBeDisplayed;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
