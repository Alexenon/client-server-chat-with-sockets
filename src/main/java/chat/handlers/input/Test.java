package chat.handlers.input;

import chat.models.User;

public class Test {

    public static void main(String[] args) {
        String message1 = "This is simple";
        String message2 = "This is complicated";
        User user = new User("Test"); // Get From Input

        InputHandlerFactory inputHandlerFactory = new InputHandlerFactory(user);
        InputHandler inputHandler = inputHandlerFactory.getInputHandler(message1, false);

        Object publicMessage = inputHandler.convertIntoObject(message1);

        InputHandler inputHandler2 = inputHandlerFactory.getInputHandler(message2, true);

        Object encryptedMessage = inputHandler2.convertIntoObject(message2);

        System.out.println(publicMessage);
        System.out.println(encryptedMessage);

    }

    public void sendToServer(Object o) {
        System.out.println("Sending " + o);
    }

}
