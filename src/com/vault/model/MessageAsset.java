package com.vault.model;

import java.time.LocalDate;

// OOP: Inheritance - extends Asset
public class MessageAsset extends Asset {
    private String message;
    private String videoLink; // optional YouTube/Drive link

    public MessageAsset(String assetId, String title, String message,
                        String assignedTo, LocalDate unlockDate, String videoLink) {
        super(assetId, title, "Personal message for heir", assignedTo, unlockDate);
        this.message = message;
        this.videoLink = videoLink;
    }

    @Override
    public String getAssetType() {
        return "Message / Video";
    }

    @Override
    public String getDisplayInfo() {
        String info = "📝 Message: " + message;
        if (videoLink != null && !videoLink.isEmpty()) {
            info += "\n   🎥 Video Link: " + videoLink;
        }
        return info;
    }

    public String getMessage()   { return message; }
    public String getVideoLink() { return videoLink; }
    public void setMessage(String message)   { this.message = message; }
    public void setVideoLink(String videoLink){ this.videoLink = videoLink; }
}
