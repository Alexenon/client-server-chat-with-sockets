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
    private SecretKey groupKey;

    public ChatKeyGenerator() throws NoSuchAlgorithmException {
        groupKey = initiateGroupKey();
    }


    // Generating a group key (symmetric)
    public SecretKey initiateGroupKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public byte[] getEncryptedGroupKey(PublicKey publicKey) throws Exception {
        return encryptGroupKey(groupKey.getEncoded(), publicKey);
    }

    /**
     * Used for encryption of group key
     * */
    private byte[] encryptGroupKey(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * Used for decryption of group key
     *
     * @param data is encrypted group key
     * @param privateKey is user personal private key
     * */
    private byte[] decryptGroupKey(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * @param groupKey should be used original group key, and not encrypted one
     * */
    public byte[] encryptMessage(String message, SecretKey groupKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, groupKey);
        byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());
        byte[] combined = new byte[iv.length + encryptedMessage.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedMessage, 0, combined, iv.length, encryptedMessage.length);
        return combined;
    }

    /**
     * @param encryptedMessage is the message that should be decrypted
     * @param key is the decrypted group key
     * */
    public String decryptMessage(byte[] encryptedMessage, byte[] key) throws Exception {
        byte[] iv = Arrays.copyOfRange(encryptedMessage, 0, 16); // IV is 16 bytes long
        byte[] encryptedMessageShorted = Arrays.copyOfRange(encryptedMessage, 16, encryptedMessage.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        byte[] decryptedMessage = cipher.doFinal(encryptedMessageShorted);
        return new String(decryptedMessage);
    }

    // Reset, the public key is regenerated, together with all private keys
    // So new private keys are generated from new public key
    public void reset() {
    }
}
