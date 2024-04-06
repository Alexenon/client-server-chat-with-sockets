package chat.models.commands;

import java.util.Objects;

public abstract class Command {

    protected final String name;
    protected final CommandType type;
    protected String[] params;

    public Command(String name, CommandType type) {
        this(name, type, null);
    }

    public Command(String name, CommandType type, String[] params) {
        this.name = name;
        this.type = type;
        this.params = Objects.requireNonNullElse(params, new String[]{});
    }

    public abstract void execute();

    public String getName() {
        return name;
    }

    public CommandType getType() {
        return type;
    }

    public String[] getParams() {
        return params;
    }
}
