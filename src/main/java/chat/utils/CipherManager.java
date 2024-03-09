package chat.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

public class CipherManager {

    private static final int IV_BYTES_LENGTH = 16;

    private static final String SECRET_KEY_CIPHER_NAME = "RSA/ECB/PKCS1Padding";
    private static final String MESSAGE_CIPHER_NAME = "AES/CBC/PKCS5Padding";

    /**
     * Used for encryption of a security key
     *
     * @param secretKey is the key, that should be encrypted
     * @param publicKey is user personal publicKey key used for encryption
     */
    public static SecretKey encryptSecretKey(SecretKey secretKey, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(SECRET_KEY_CIPHER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(secretKey.getEncoded());
            return new SecretKeySpec(encrypted, cipher.getAlgorithm());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used for decryption of a security key
     *
     * @param encryptedKey is the encrypted secret key that should be decrypted
     * @param privateKey   is user personal private key used for decryption
     */
    public static SecretKey decryptSecretKey(SecretKey encryptedKey, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(SECRET_KEY_CIPHER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypted = cipher.doFinal(encryptedKey.getEncoded());
            return new SecretKeySpec(decrypted, cipher.getAlgorithm());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(String text, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(MESSAGE_CIPHER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedMessage = cipher.doFinal(text.getBytes());
            byte[] combined = new byte[iv.length + encryptedMessage.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedMessage, 0, combined, iv.length, encryptedMessage.length);
            return combined;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] encryptedMessage, SecretKey secretKey) {
        try {
            byte[] key = secretKey.getEncoded();
            byte[] iv = Arrays.copyOfRange(encryptedMessage, 0, IV_BYTES_LENGTH);
            byte[] encryptedMessageShorted = Arrays.copyOfRange(encryptedMessage, IV_BYTES_LENGTH, encryptedMessage.length);
            Cipher cipher = Cipher.getInstance(MESSAGE_CIPHER_NAME);

            System.out.println("ALGO: " + cipher.getAlgorithm());

            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            return cipher.doFinal(encryptedMessageShorted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
