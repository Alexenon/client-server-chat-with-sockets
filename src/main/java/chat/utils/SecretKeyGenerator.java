package chat.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class SecretKeyGenerator {

    /**
     * Generate a symmetric key for AES encryption
     */
    public static SecretKey generateSymmetricKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    /**
     * Encrypt message using AES symmetric encryption
     */
    public static KeyPair generateAsymmetricKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // You can adjust key size as needed
        return keyGen.generateKeyPair();
    }

    /**
     * Encrypt message using AES symmetric encryption
     */
    public static byte[] encryptMessage(byte[] message, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }

    /**
     * Decrypt message using AES symmetric decryption
     */
    public static byte[] decryptMessage(byte[] encryptedMessage, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedMessage);
    }

    public static byte[] encryptPrivateMessage(byte[] message, PublicKey publicKey) {
        return null;
    }

    public static byte[] decryptPrivateMessage(byte[] encryptedMessage, PublicKey privateKey) {
        return null;
    }


}
