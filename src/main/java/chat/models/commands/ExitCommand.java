package chat.models.commands;

public class ExitCommand extends Command {
    String input;

    public ExitCommand(String input) {
        super(input);
        this.input = input;
    }

    public void execute() {
        System.out.println("Executing \"" + input + "\" command");
    }

    public boolean isValid() {
        return input.equalsIgnoreCase("/exit");
    }

    public String getResult() {
        return !isValid()
                ? getErrorMessage()
                : "";
    }

    private String getErrorMessage() {
        return "";
    }

}
