import chat.models.User;

import javax.crypto.SecretKey;

public class EncryptDecrypt {
    public static void main(String[] args) {
        User dan = new User("Dan");
        User alex = new User("Alex");

        ChatKeyGenerator keyGenerator = new ChatKeyGenerator();
        SecretKey groupKey = keyGenerator.getGroupKey();

        byte[] encryptedGroupKey_for_Dan = keyGenerator.encryptGroupKey(groupKey.getEncoded(), dan.getPublicKey());
        byte[] decryptedGroupKey_for_Dan = keyGenerator.decryptGroupKey(encryptedGroupKey_for_Dan, dan.getPrivateKey());

        byte[] encryptedGroupKey_for_Alex = keyGenerator.encryptGroupKey(groupKey.getEncoded(), alex.getPublicKey());
        byte[] decryptedGroupKey_for_Alex = keyGenerator.decryptGroupKey(encryptedGroupKey_for_Alex, alex.getPrivateKey());

        String messageFromAlex = "Hi Dan, this should our secret";
        String messageFromDan = "Hi Alex, yes, I know this";

        byte[] encrypted_messageFromAlex = keyGenerator.encryptMessage(messageFromAlex, groupKey);
        String decrypted_messageFromAlex = keyGenerator.decryptMessage(encrypted_messageFromAlex, decryptedGroupKey_for_Dan);

        byte[] encrypted_messageFromDan = keyGenerator.encryptMessage(messageFromDan, groupKey);
        String decrypted_messageFromDan = keyGenerator.decryptMessage(encrypted_messageFromDan, decryptedGroupKey_for_Alex);

        System.out.println("Sending message and encrypted group key to " + dan);
        System.out.println(" - Encrypted message: " + new String(encrypted_messageFromAlex));
        System.out.println(" - Decrypted message: " + decrypted_messageFromAlex);
        System.out.println(" - Encrypted group key (for " + dan + "): " + new String(encryptedGroupKey_for_Dan));

        System.out.println("\n");

        System.out.println("Sending message and encrypted group key to " + alex);
        System.out.println(" - Encrypted message: " + new String(encrypted_messageFromDan));
        System.out.println(" - Decrypted message: " + decrypted_messageFromDan);
        System.out.println(" - Encrypted group key (for " + dan + "): " + new String(encryptedGroupKey_for_Alex));
    }
}
