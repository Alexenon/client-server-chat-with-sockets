package chat.utils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String text;
    private final User author;
    private final User designator;
    private final LocalDate date;

    public Message(String text, User author, User designator) {
        this.text = text;
        this.author = author;
        this.designator = designator;
        this.date = LocalDate.now();
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public User getDesignator() {
        return designator;
    }

    public LocalDate getDate() {
        return date;
    }

}
