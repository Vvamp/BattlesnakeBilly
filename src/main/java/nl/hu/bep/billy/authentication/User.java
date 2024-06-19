package nl.hu.bep.billy.authentication;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;

public class User {
    private final String username;
    private final String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
