package chat.models;

import java.io.Serial;
import java.io.Serializable;
import java.security.*;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 195640213275933887L;

    private final String username;
    private final KeyPair keyPair;

    public User(String username) {
        this.username = username;
        this.keyPair = generateKeyPair();
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException");
        }
    }

    public String getUsername() {
        return username;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
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

        User u = (User) o;

        return username.compareTo(u.username) == 0
               && Objects.equals(keyPair, u.keyPair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, keyPair);
    }
}