import chat.models.EncryptedMessage;
import chat.models.Message;
import chat.models.User;

public class Main {
    public static void main(String[] args) {
        System.out.println(getObjectMessage(true));
        System.out.println(getObjectMessage(false));
    }

    private static Object getObjectMessage(boolean isEncrypted) {
        Message messageToBeSent = new Message("Hello, world!",
                new User("Alice"), new User("Bob"));
        EncryptedMessage messageToBeSentEncrypted = new EncryptedMessage("Hello, world!",
                new User("Alice"), new User("Bob"));

        return isEncrypted ? messageToBeSentEncrypted : messageToBeSent;
    }

}
