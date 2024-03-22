package chat.models.commands;

public class ExitCommand implements Command {
    String input;

    public ExitCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.println("Executing \"" + input + "\" command");
    }

    @Override
    public boolean isValid() {
        return !input.equals("/exit");
    }

    @Override
    public String getResult() {
        return !isValid()
                ? getErrorMessage()
                : "";
    }

}
