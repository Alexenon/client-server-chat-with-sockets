package chat.handlers.input;

import chat.models.Command;
import chat.models.User;

public class Test {
    User user = new User("test");

    public void test() {
        String input = getFieldInput();

        CommandInputHandler commandInputHandler = new CommandInputHandler(input);
        Command command = commandInputHandler.getCommand().orElseThrow();

        CommandOutputHandler commandOutputHandler = new CommandOutputHandler(command);
        Object commandResponse = commandOutputHandler.getResponse();

    }

    public void sendToServer(Object o) {
        System.out.println("Sending " + o);
    }

    private String getFieldInput() {
        return "";
    }
}
