package com.vault.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// OOP: Encapsulation
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String name;
    private String email;
    private String passwordHash; // store hash, not plain password
    private String role;         // "OWNER" or "HEIR"
    private List<String> assetIds; // IDs of assets this user owns/is heir to

    public User(String userId, String name, String email,
                String passwordHash, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.assetIds = new ArrayList<>();
    }

    public void addAssetId(String assetId) {
        if (!assetIds.contains(assetId)) assetIds.add(assetId);
    }

    // Getters
    public String getUserId()       { return userId; }
    public String getName()         { return name; }
    public String getEmail()        { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole()         { return role; }
    public List<String> getAssetIds(){ return assetIds; }

    // Setters
    public void setName(String name)               { this.name = name; }
    public void setEmail(String email)             { this.email = email; }
    public void setPasswordHash(String hash)       { this.passwordHash = hash; }

    @Override
    public String toString() {
        return String.format("User[%s] %s (%s) | Role: %s", userId, name, email, role);
    }
}
