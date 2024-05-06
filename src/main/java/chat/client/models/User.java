package chat.client.models;

import chat.EncryptUtils;

import java.io.Serial;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 195640213275933887L;

    private final String username;
    private KeyPair keyPair;

    public User(String username) {
        this.username = username;
        this.keyPair = EncryptUtils.generateKeyPair();
    }

    public String getUsername() {
        return username;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
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

        return username.equals(u.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, keyPair);
    }
}