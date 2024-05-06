package chat.client.models;

import chat.utils.ServerConfiguration;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 8322154030015983245L;

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

    @Override
    public String toString() {
        return "Message{" +
               "text='" + text.replaceAll("\n", " ") + '\'' +
               ", author=" + author +
               ", receiver=" + receiver +
               ", dateTime=" + dateTime.format(ServerConfiguration.DATE_TIME_FORMATTER) +
               '}';
    }
}
