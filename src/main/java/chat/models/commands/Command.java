package chat.models.commands;

import java.util.Arrays;

public abstract class Command {

    protected CommandType type;
    protected String[] params;

    public Command(String input) {
        this.type = getTypeFromInput(input);
        this.params = getParamsFromInput(input);
    }

    protected CommandType getTypeFromInput(String input) {
        return Arrays.stream(CommandType.values())
                .filter(c -> c.name().equalsIgnoreCase(getCommandName(input)))
                .findFirst()
                .orElse(null);
    }

    protected String[] getParamsFromInput(String input) {
        return Arrays.stream(input.split(" "))
                .skip(1)
                .toArray(String[]::new);
    }

    protected String getCommandName(String input) {
        return input.split(" ")[0]
                .replace("/", "")
                .toLowerCase()
                .trim();
    }

    public CommandType getType() {
        return type;
    }

    public String[] getParams() {
        return params;
    }
}
