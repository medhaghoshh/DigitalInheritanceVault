package com.vault.model;

import java.io.Serializable;
import java.time.LocalDate;

// Abstract base class - OOP: Abstraction + Inheritance
public abstract class Asset implements Serializable {
    private static final long serialVersionUID = 1L;

    private String assetId;
    private String title;
    private String description;
    private String assignedTo;     // heir's name
    private LocalDate unlockDate;  // date when heir can access
    private boolean isLocked;

    public Asset(String assetId, String title, String description,
                 String assignedTo, LocalDate unlockDate) {
        this.assetId = assetId;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.unlockDate = unlockDate;
        this.isLocked = true;
    }

    // Abstract method — each asset type implements differently
    public abstract String getAssetType();
    public abstract String getDisplayInfo();

    // Check if asset should be unlocked today
    public boolean shouldUnlock() {
        return !LocalDate.now().isBefore(unlockDate);
    }

    public void unlock() {
        if (shouldUnlock()) {
            this.isLocked = false;
        }
    }

    // Getters & Setters
    public String getAssetId()         { return assetId; }
    public String getTitle()           { return title; }
    public String getDescription()     { return description; }
    public String getAssignedTo()      { return assignedTo; }
    public LocalDate getUnlockDate()   { return unlockDate; }
    public boolean isLocked()          { return isLocked; }

    public void setTitle(String title)             { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setAssignedTo(String assignedTo)   { this.assignedTo = assignedTo; }
    public void setUnlockDate(LocalDate unlockDate){ this.unlockDate = unlockDate; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Type: %s | Heir: %s | Unlock: %s | Status: %s",
                assetId, title, getAssetType(), assignedTo, unlockDate,
                isLocked ? "🔒 Locked" : "🔓 Unlocked");
    }
}
