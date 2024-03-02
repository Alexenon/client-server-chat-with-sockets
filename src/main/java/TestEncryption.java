import chat.models.EncryptedMessage;
import chat.models.User;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class TestEncryption {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        User author = new User("Alice");
        User receiver = new User("Bob");

        String messageText = "Hello, Bob!";
        EncryptedMessage message = new EncryptedMessage(messageText, author, receiver, keyPair.getPublic());

        String decryptedMessage = message.getText(keyPair.getPrivate());

        // Output
        System.out.println("Original message: " + messageText);
        System.out.println("Encrypted message: " + message.getText());
        System.out.println("Decrypted message: " + decryptedMessage);

    }

}
