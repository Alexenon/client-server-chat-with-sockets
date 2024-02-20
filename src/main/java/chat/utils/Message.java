package chat.utils;

public class Message {
    String text;
    User author;
    User designator;
    String date;
    boolean isPrivate;

    public Message(String text, User author, User designator, String date, boolean isPrivate) {
        this.text = text;
        this.author = author;
        this.designator = designator;
        this.date = date;
        this.isPrivate = isPrivate;
    }
}
