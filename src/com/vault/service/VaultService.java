package com.vault.service;

import com.vault.model.*;
import com.vault.util.FileHandler;
import com.vault.util.PasswordUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// OOP: Single Responsibility - handles all vault business logic
public class VaultService {

    private Map<String, User> users;    // userId -> User
    private Map<String, Asset> assets;  // assetId -> Asset
    private User currentUser;

    private static final String USERS_FILE  = "data/users.dat";
    private static final String ASSETS_FILE = "data/assets.dat";

    @SuppressWarnings("unchecked")
    public VaultService() {
        // Load persisted data from files
        Object loadedUsers  = FileHandler.load(USERS_FILE);
        Object loadedAssets = FileHandler.load(ASSETS_FILE);

        users  = (loadedUsers  != null) ? (Map<String, User>)  loadedUsers  : new HashMap<>();
        assets = (loadedAssets != null) ? (Map<String, Asset>) loadedAssets : new HashMap<>();

        // Auto-unlock assets whose date has arrived
        autoUnlockAssets();
    }

    // ─── AUTH ────────────────────────────────────────────────────────────────

    public boolean register(String name, String email, String password, String role) {
        boolean emailExists = users.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (emailExists) return false;

        String userId = "U" + (users.size() + 1);
        String hash   = PasswordUtil.hash(password);
        User user     = new User(userId, name, email, hash, role.toUpperCase());
        users.put(userId, user);
        save();
        return true;
    }

    public boolean login(String email, String password) {
        String hash = PasswordUtil.hash(password);
        Optional<User> found = users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email)
                          && u.getPasswordHash().equals(hash))
                .findFirst();
        if (found.isPresent()) {
            currentUser = found.get();
            return true;
        }
        return false;
    }

    public void logout() { currentUser = null; }

    public User getCurrentUser() { return currentUser; }

    // ─── ASSET CRUD ──────────────────────────────────────────────────────────

    public Asset addMessageAsset(String title, String message, String assignedTo,
                                  LocalDate unlockDate, String videoLink) {
        String id = generateAssetId();
        MessageAsset asset = new MessageAsset(id, title, message, assignedTo, unlockDate, videoLink);
        assets.put(id, asset);
        currentUser.addAssetId(id);
        save();
        return asset;
    }

    public Asset addFinancialAsset(String title, double amount, String bank,
                                    String accountHint, String assignedTo, LocalDate unlockDate) {
        String id = generateAssetId();
        FinancialAsset asset = new FinancialAsset(id, title, amount, bank, accountHint, assignedTo, unlockDate);
        assets.put(id, asset);
        currentUser.addAssetId(id);
        save();
        return asset;
    }

    public Asset addDocumentAsset(String title, String docType, String location,
                                   String notes, String assignedTo, LocalDate unlockDate) {
        String id = generateAssetId();
        DocumentAsset asset = new DocumentAsset(id, title, docType, location, notes, assignedTo, unlockDate);
        assets.put(id, asset);
        currentUser.addAssetId(id);
        save();
        return asset;
    }

    public boolean deleteAsset(String assetId) {
        if (!assets.containsKey(assetId)) return false;
        assets.remove(assetId);
        currentUser.getAssetIds().remove(assetId);
        save();
        return true;
    }

    // ─── VIEW ────────────────────────────────────────────────────────────────

    // Owner sees ALL their assets
    public List<Asset> getMyAssets() {
        return currentUser.getAssetIds().stream()
                .map(assets::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // Heir sees only UNLOCKED assets assigned to them
    public List<Asset> getHeirAssets() {
        String heirName = currentUser.getName();
        return assets.values().stream()
                .filter(a -> a.getAssignedTo().equalsIgnoreCase(heirName) && !a.isLocked())
                .collect(Collectors.toList());
    }

    // ─── AUTO UNLOCK ─────────────────────────────────────────────────────────

    private void autoUnlockAssets() {
        boolean changed = false;
        for (Asset asset : assets.values()) {
            if (asset.isLocked() && asset.shouldUnlock()) {
                asset.unlock();
                changed = true;
            }
        }
        if (changed) save();
    }

    // ─── UTILS ───────────────────────────────────────────────────────────────

    private String generateAssetId() {
        return "A" + System.currentTimeMillis();
    }

    private void save() {
        FileHandler.save(users, USERS_FILE);
        FileHandler.save(assets, ASSETS_FILE);
    }

    public Map<String, Asset> getAllAssets() { return assets; }
}
