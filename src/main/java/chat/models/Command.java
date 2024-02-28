package chat.models;

public class Command {
    String text;
    String[] flags;

    public Command(String fullCommand) {

    }

    public String getText() {
        return text;
    }

    public String[] getFlags() {
        return flags;
    }
}
