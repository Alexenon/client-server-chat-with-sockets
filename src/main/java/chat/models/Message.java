package chat.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 8322154030015983245L;
    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm";

    private final String text;
    private final User author;
    private final User receiver;
    private final LocalDateTime dateTime;

    public Message(String text) {
        this(text, null, null);
    }

    public Message(String text, User author, User receiver) {
        this.text = text;
        this.author = author;
        this.receiver = receiver;
        this.dateTime = LocalDateTime.now();
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public User getReceiver() {
        return receiver;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeFormatted() {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER));
    }

    @Override
    public String toString() {
        return "Message{" +
               "text='" + text + '\'' +
               ", author=" + author +
               ", receiver=" + receiver +
               ", dateTime=" + getDateTimeFormatted() +
               '}';
    }
}
