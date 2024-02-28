package chat.handlers.input;

import chat.models.User;

public class InputObjectFactory {
    private final User user;

    public InputObjectFactory(User user) {
        this.user = user;
    }

    public InputHandler getInputHandler(String input) {
        return new MessageInputHandler(user);
    }

}
