package chat.client.models.commands;

import java.util.Objects;

public abstract class Command {
    protected String[] params;
    protected final CommandType type;

    public Command(CommandType type) {
        this(type, null);
    }

    public Command(CommandType type, String[] params) {
        this.type = type;
        this.params = Objects.requireNonNullElse(params, new String[]{});
    }

    public abstract void execute();

    public CommandType getType() {
        return type;
    }

    public String[] getParams() {
        return params;
    }
}
