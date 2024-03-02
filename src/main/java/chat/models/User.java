package chat.models;

import java.io.Serial;
import java.io.Serializable;
import java.security.*;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private final String username;
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public User(String username) {
        this.username = username;
        KeyPair keyPair = generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    private KeyPair generateKeyPair() {
        KeyPair keyPair = null;

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return keyPair;
    }

    public String getUsername() {
        return username;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(this.username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}