import chat.EncryptUtils;
import chat.client.models.User;

import javax.crypto.SecretKey;

public class EncryptDecrypt {
    public static void main(String[] args) {
        User dan = new User("Dan");
        User alex = new User("Alex");

        SecretKey groupKey = EncryptUtils.initiateGroupKey();

        byte[] encryptedGroupKey_for_Dan = EncryptUtils.encryptSecretKey(groupKey.getEncoded(), dan.getPublicKey());
        byte[] decryptedGroupKey_for_Dan = EncryptUtils.decryptSecretKey(encryptedGroupKey_for_Dan, dan.getPrivateKey());

        byte[] encryptedGroupKey_for_Alex = EncryptUtils.encryptSecretKey(groupKey.getEncoded(), alex.getPublicKey());
        byte[] decryptedGroupKey_for_Alex = EncryptUtils.decryptSecretKey(encryptedGroupKey_for_Alex, alex.getPrivateKey());

        String messageFromAlex = "Hi Dan, this should our secret";
        String messageFromDan = "Hi Alex, yes, I know this";

        byte[] encrypted_messageFromAlex = EncryptUtils.encryptMessageWithKey(messageFromAlex, groupKey);
        String decrypted_messageFromAlex = EncryptUtils.decryptMessageWithEncryptedKey(encrypted_messageFromAlex, decryptedGroupKey_for_Dan);

        byte[] encrypted_messageFromDan = EncryptUtils.encryptMessageWithKey(messageFromDan, groupKey);
        String decrypted_messageFromDan = EncryptUtils.decryptMessageWithEncryptedKey(encrypted_messageFromDan, decryptedGroupKey_for_Alex);

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
