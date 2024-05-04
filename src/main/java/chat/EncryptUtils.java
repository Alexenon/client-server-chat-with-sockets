package chat;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.util.Base64;

public class EncryptUtils {
    private static final int KEY_SIZE = 256;
    private static final String SYMMETRIC_ALGORITHM = "AES";
    private static final String SYMMETRIC_ENCRYPTION_CIPHER = "AES/CBC/PKCS5Padding";
    private static final String ASYMMETRIC_ALGORITHM = "RSA";
    private static final String ASYMMETRIC_ENCRYPTION_CIPHER = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    public static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(KEY_SIZE, secureRandom);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating AES key", e);
        }
    }

    public static String encrypt(String text, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(SYMMETRIC_ENCRYPTION_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            byte[] combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(SYMMETRIC_ENCRYPTION_CIPHER);
            byte[] combined = Base64.getDecoder().decode(encryptedText);
            byte[] iv = new byte[cipher.getBlockSize()];
            byte[] encryptedBytes = new byte[combined.length - iv.length];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            System.arraycopy(combined, iv.length, encryptedBytes, 0, encryptedBytes.length);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ASYMMETRIC_ALGORITHM);
            keyGen.initialize(2048);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        }
    }

    public static String encrypt(String plainText, PublicKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_ENCRYPTION_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    public static String decrypt(String encryptedText, PrivateKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_ENCRYPTION_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }
}
