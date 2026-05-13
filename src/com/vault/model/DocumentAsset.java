package com.vault.model;

import java.time.LocalDate;

// OOP: Inheritance - extends Asset
public class DocumentAsset extends Asset {
    private String documentType; // Will, Property, Insurance, etc.
    private String location;     // Physical or digital location
    private String notes;

    public DocumentAsset(String assetId, String title, String documentType,
                         String location, String notes,
                         String assignedTo, LocalDate unlockDate) {
        super(assetId, title, "Important document", assignedTo, unlockDate);
        this.documentType = documentType;
        this.location = location;
        this.notes = notes;
    }

    @Override
    public String getAssetType() {
        return "Document (" + documentType + ")";
    }

    @Override
    public String getDisplayInfo() {
        return String.format("📄 Doc Type: %s | Location: %s | Notes: %s",
                documentType, location, notes);
    }

    public String getDocumentType() { return documentType; }
    public String getLocation()     { return location; }
    public String getNotes()        { return notes; }
    public void setDocumentType(String documentType){ this.documentType = documentType; }
    public void setLocation(String location)        { this.location = location; }
    public void setNotes(String notes)              { this.notes = notes; }
}
