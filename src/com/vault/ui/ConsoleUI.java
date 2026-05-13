package com.vault.ui;

import com.vault.model.*;
import com.vault.service.VaultService;
import com.vault.util.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

// OOP: Separation of UI from business logic
public class ConsoleUI {

    private final VaultService service;
    private final Scanner sc;

    public ConsoleUI() {
        service = new VaultService();
        sc = new Scanner(System.in);
    }

    public void start() {
        banner();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt();
            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> { System.out.println("\n👋 Goodbye! Your legacy is safe."); running = false; }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    // ─── AUTH MENUS ──────────────────────────────────────────────────────────

    private void register() {
        System.out.println("\n╔══ REGISTER ══╗");
        System.out.print("Name     : "); String name = sc.nextLine().trim();
        System.out.print("Email    : "); String email = sc.nextLine().trim();
        System.out.print("Password : "); String pass  = sc.nextLine().trim();
        System.out.print("Role (OWNER / HEIR): "); String role = sc.nextLine().trim().toUpperCase();

        if (!role.equals("OWNER") && !role.equals("HEIR")) {
            System.out.println("❌ Role must be OWNER or HEIR.");
            return;
        }
        if (service.register(name, email, pass, role)) {
            System.out.println("✅ Registered successfully! Please log in.");
        } else {
            System.out.println("❌ Email already exists.");
        }
    }

    private void login() {
        System.out.println("\n╔══ LOGIN ══╗");
        System.out.print("Email    : "); String email = sc.nextLine().trim();
        System.out.print("Password : "); String pass  = sc.nextLine().trim();

        if (service.login(email, pass)) {
            User user = service.getCurrentUser();
            System.out.println("✅ Welcome, " + user.getName() + "! [" + user.getRole() + "]");
            if (user.getRole().equals("OWNER")) {
                ownerDashboard();
            } else {
                heirDashboard();
            }
        } else {
            System.out.println("❌ Invalid email or password.");
        }
    }

    // ─── OWNER DASHBOARD ─────────────────────────────────────────────────────

    private void ownerDashboard() {
        boolean active = true;
        while (active) {
            System.out.println("""
                    
                    ╔══════════════════════════════╗
                    ║      OWNER DASHBOARD         ║
                    ╠══════════════════════════════╣
                    ║  1. Add Message / Video Asset ║
                    ║  2. Add Financial Asset       ║
                    ║  3. Add Document Asset        ║
                    ║  4. View My Assets            ║
                    ║  5. Delete an Asset           ║
                    ║  6. Logout                    ║
                    ╚══════════════════════════════╝""");
            int ch = readInt();
            switch (ch) {
                case 1 -> addMessageAsset();
                case 2 -> addFinancialAsset();
                case 3 -> addDocumentAsset();
                case 4 -> viewMyAssets();
                case 5 -> deleteAsset();
                case 6 -> { service.logout(); active = false; System.out.println("🔒 Logged out."); }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    private void addMessageAsset() {
        System.out.println("\n── Add Message / Video ──");
        System.out.print("Title          : "); String title   = sc.nextLine().trim();
        System.out.print("Message        : "); String message = sc.nextLine().trim();
        System.out.print("Video Link (or press Enter to skip): "); String video = sc.nextLine().trim();
        System.out.print("Assigned To (Heir Name): "); String heir = sc.nextLine().trim();
        LocalDate date = readDate();

        Asset a = service.addMessageAsset(title, message, heir, date, video);
        System.out.println("✅ Asset added! ID: " + a.getAssetId());
    }

    private void addFinancialAsset() {
        System.out.println("\n── Add Financial Asset ──");
        System.out.print("Title          : "); String title = sc.nextLine().trim();
        System.out.print("Bank Name      : "); String bank  = sc.nextLine().trim();
        System.out.print("Account Hint   : "); String hint  = sc.nextLine().trim();
        System.out.print("Amount (₹)     : "); double amt = readDouble();
        System.out.print("Assigned To    : "); String heir = sc.nextLine().trim();
        LocalDate date = readDate();

        Asset a = service.addFinancialAsset(title, amt, bank, hint, heir, date);
        System.out.println("✅ Asset added! ID: " + a.getAssetId());
    }

    private void addDocumentAsset() {
        System.out.println("\n── Add Document Asset ──");
        System.out.print("Title          : "); String title   = sc.nextLine().trim();
        System.out.print("Document Type  : "); String docType = sc.nextLine().trim();
        System.out.print("Location       : "); String loc     = sc.nextLine().trim();
        System.out.print("Notes          : "); String notes   = sc.nextLine().trim();
        System.out.print("Assigned To    : "); String heir    = sc.nextLine().trim();
        LocalDate date = readDate();

        Asset a = service.addDocumentAsset(title, docType, loc, notes, heir, date);
        System.out.println("✅ Asset added! ID: " + a.getAssetId());
    }

    private void viewMyAssets() {
        List<Asset> myAssets = service.getMyAssets();
        if (myAssets.isEmpty()) {
            System.out.println("\n📭 No assets in your vault yet.");
            return;
        }
        System.out.println("\n═══ YOUR VAULT ASSETS ═══");
        for (Asset a : myAssets) {
            System.out.println(a);
            System.out.println("   " + a.getDisplayInfo());
            System.out.println("──────────────────────────────");
        }
    }

    private void deleteAsset() {
        viewMyAssets();
        System.out.print("Enter Asset ID to delete: "); String id = sc.nextLine().trim();
        if (service.deleteAsset(id)) {
            System.out.println("✅ Asset deleted.");
        } else {
            System.out.println("❌ Asset not found.");
        }
    }

    // ─── HEIR DASHBOARD ──────────────────────────────────────────────────────

    private void heirDashboard() {
        boolean active = true;
        while (active) {
            System.out.println("""
                    
                    ╔══════════════════════════════╗
                    ║      HEIR DASHBOARD          ║
                    ╠══════════════════════════════╣
                    ║  1. View My Inherited Assets  ║
                    ║  2. Logout                    ║
                    ╚══════════════════════════════╝""");
            int ch = readInt();
            switch (ch) {
                case 1 -> viewHeirAssets();
                case 2 -> { service.logout(); active = false; System.out.println("🔒 Logged out."); }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    private void viewHeirAssets() {
        List<Asset> heirAssets = service.getHeirAssets();
        if (heirAssets.isEmpty()) {
            System.out.println("\n📭 No unlocked assets assigned to you yet.");
            return;
        }
        System.out.println("\n═══ YOUR INHERITED ASSETS ═══");
        for (Asset a : heirAssets) {
            System.out.println(a);
            System.out.println("   " + a.getDisplayInfo());
            System.out.println("──────────────────────────────");
        }
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private void banner() {
        System.out.println("""
                ╔════════════════════════════════════════════╗
                ║     🏛️  DIGITAL INHERITANCE VAULT 🏛️        ║
                ║   Secure Your Legacy. Empower Your Heirs.  ║
                ╚════════════════════════════════════════════╝
                """);
    }

    private void printMainMenu() {
        System.out.println("""
                ┌──────────────────────┐
                │  1. Register         │
                │  2. Login            │
                │  3. Exit             │
                └──────────────────────┘
                Choice: """);
    }

    private int readInt() {
        try {
            int val = Integer.parseInt(sc.nextLine().trim());
            return val;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double readDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Invalid amount, defaulting to 0.");
            return 0;
        }
    }

    private LocalDate readDate() {
        while (true) {
            System.out.print("Unlock Date (dd-MM-yyyy): ");
            try {
                return DateUtil.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid format. Use dd-MM-yyyy. Try again.");
            }
        }
    }
}
