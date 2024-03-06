import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.*;

public class GroupChatSimulator {

    public static void main(String[] args) throws Exception {
        // Simulate group members
        Map<String, KeyPair> members = new HashMap<>();
        members.put("Alice", generateKeyPair());
        members.put("Bob", generateKeyPair());
        members.put("Charlie", generateKeyPair());

        // Simulate generating a group key (symmetric)
        SecretKey groupKey = generateAESKey();

        // Encrypt the group key with each member's public key
        Map<String, byte[]> encryptedGroupKeys = new HashMap<>();
        for (String member : members.keySet()) {
            encryptedGroupKeys.put(member, encrypt(groupKey.getEncoded(), members.get(member).getPublic()));
        }

        // Simulate sending a message to the group
        String message = "Hello everyone!";

        // Encrypt the message with the group key (symmetric)
        byte[] encryptedMessage = symmetricEncrypt(message, groupKey);

        // Simulate sending the message and encrypted group key to each member
        for (String member : members.keySet()) {
            System.out.println("Sending message and encrypted group key to " + member);
            System.out.println(" - Encrypted message: " + new String(encryptedMessage));
            System.out.println(" - Encrypted group key (for " + member + "): " + new String(encryptedGroupKeys.get(member)));
        }

        // Simulate a member decrypting the message and group key
        String recipient = "Bob";
        byte[] decryptedGroupKey = decrypt(encryptedGroupKeys.get(recipient), members.get(recipient).getPrivate());
        String decryptedMessage = symmetricDecrypt(encryptedMessage, decryptedGroupKey);

        System.out.println("\n" + recipient + " decrypts the message:");
        System.out.println(" - Decrypted group key: " + new String(decryptedGroupKey));
        System.out.println(" - Decrypted message: " + decryptedMessage);
        System.out.println(" - Encrypted message: " + new String(encryptedMessage));
    }

    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    private static byte[] symmetricEncrypt(String message, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());
        byte[] combined = new byte[iv.length + encryptedMessage.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedMessage, 0, combined, iv.length, encryptedMessage.length);
        return combined;
    }

    private static String symmetricDecrypt(byte[] data, byte[] key) throws Exception {
        byte[] iv = Arrays.copyOfRange(data, 0, 16); // IV is 16 bytes long
        byte[] encryptedMessage = Arrays.copyOfRange(data, 16, data.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
        return new String(decryptedMessage);
    }
}
