package chat;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class EncryptUtils {
    private static final int KEY_SIZE = 256;
    private static final String ALGORITHM = "AES";
    private static final String MESSAGE_ENCRYPTION_CIPHER = "AES/CBC/PKCS5Padding";

    /**
     * Generating a symmetric AES key
     */
    public static SecretKey initiateGroupKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(KEY_SIZE, secureRandom);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating AES key", e);
        }
    }

    public static String encrypt(String text, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Decryption error", e);
        }
    }


    /**
     * Used for encryption of group key
     */
    public static byte[] encryptSecretKey(byte[] keyToEncrypted, PublicKey publicKey) {
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
    public static byte[] decryptSecretKey(byte[] keyToBeDecrypted, PrivateKey privateKey) {
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
    public static byte[] encryptMessageWithKey(String message, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(MESSAGE_ENCRYPTION_CIPHER);
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
    public static String decryptMessageWithEncryptedKey(byte[] encryptedMessage, byte[] key) {
        try {
            byte[] iv = Arrays.copyOfRange(encryptedMessage, 0, 16); // IV is 16 bytes long
            byte[] encryptedMessageShorted = Arrays.copyOfRange(encryptedMessage, 16, encryptedMessage.length);
            Cipher cipher = Cipher.getInstance(MESSAGE_ENCRYPTION_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] decryptedMessage = cipher.doFinal(encryptedMessageShorted);
            return new String(decryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
