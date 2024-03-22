package chat.handlers.input;

import chat.models.Message;

public class InputConvertor {
    public final String PRIVATE_MESSAGE_REGEX = "\\b(\\w+):\\s+(.*)";

    public Object convertIntoObject(String input) {
        Convertor convertor = getConvertor(input);
        return convertor.convertIntoObject(input);
    }

    private Convertor getConvertor(String input) {
        if (isCommand(input)) return new CommandConvertor();
        if (isPrivateMessage(input)) return new PrivateMessageConvertor();
        return new PublicMessageConvertor();
    }

    public boolean isCommand(String input) {
        return input.startsWith("/");
    }

    public boolean isPrivateMessage(String input) {
        return input.matches(PRIVATE_MESSAGE_REGEX);
    }
}

interface Convertor {
    Object convertIntoObject(String input);
}

class PublicMessageConvertor implements Convertor {
    @Override
    public Object convertIntoObject(String input) {
        return new Message(input);
    }
}

class PrivateMessageConvertor implements Convertor {
    @Override
    public Object convertIntoObject(String input) {
        return null; // TODO
    }
}

class CommandConvertor implements Convertor {
    @Override
    public Object convertIntoObject(String input) {
        return null; // TODO
    }
}