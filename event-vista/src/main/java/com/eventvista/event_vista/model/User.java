package com.eventvista.event_vista.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User extends AbstractEntity {
    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String pwHash;

    // Static method to use the bcrypt dependency for encoding
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.pwHash = encoder.encode(password);
    }

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull String getPwHash() {
        return pwHash;
    }

    public void setPwHash(@NotNull String pwHash) {
        this.pwHash = pwHash;
    }


    // Instance method to use the bcrypt multi-step matcher (.equals is not enough)
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}

