import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

public class ChatKeyGenerator {
    private SecretKey secretKey;

    public ChatKeyGenerator() {
        secretKey = initiateGroupKey();
    }

    // Generating a group key (symmetric)
    private SecretKey initiateGroupKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException");
        }
    }

    /**
     * Used for encryption of group key
     */
    public byte[] encryptSecretKey(byte[] keyToEncrypted, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(keyToEncrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used for decryption of group key
     *
     * @param keyToBeDecrypted is encrypted group key
     * @param privateKey       is user personal private key
     */
    public byte[] decryptSecretKey(byte[] keyToBeDecrypted, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(keyToBeDecrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param secretKey should be used original group key, and not encrypted one
     */
    public byte[] encryptMessage(String message, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedMessage = cipher.doFinal(message.getBytes());
            byte[] combined = new byte[iv.length + encryptedMessage.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedMessage, 0, combined, iv.length, encryptedMessage.length);
            return combined;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param encryptedMessage is the message that should be decrypted
     * @param key              is the decrypted group key
     */
    public String decryptMessage(byte[] encryptedMessage, byte[] key) {
        try {
            byte[] iv = Arrays.copyOfRange(encryptedMessage, 0, 16); // IV is 16 bytes long
            byte[] encryptedMessageShorted = Arrays.copyOfRange(encryptedMessage, 16, encryptedMessage.length);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] decryptedMessage = cipher.doFinal(encryptedMessageShorted);
            return new String(decryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

}
