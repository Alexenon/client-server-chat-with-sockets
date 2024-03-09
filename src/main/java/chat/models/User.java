package chat.models;

import chat.utils.CipherManager;

import java.io.Serial;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 8920109496869176060L;
    private static final CipherManager cipherManager = CipherManager.getInstance();

    private String username;
    private KeyPair keyPair;

    public User(String username) {
        this(username, cipherManager.generateKeyPair());
    }

    public User(String username, KeyPair keyPair) {
        this.username = username;
        this.keyPair = keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

        User user = (User) o;

        return Objects.equals(this.username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}