package com.eventvista.event_vista.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User extends AbstractEntity {
    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    // Static method to use the bcrypt dependency for encoding
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public String getUsername() {
        return username;
    }

    // Instance method to use the bcrypt multi-step matcher (.equals is not enough)
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }


}

