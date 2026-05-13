package com.vault.model;

import java.time.LocalDate;

// OOP: Inheritance - extends Asset
public class FinancialAsset extends Asset {
    private double amount;
    private String bankName;
    private String accountHint; // partial account info for security

    public FinancialAsset(String assetId, String title, double amount,
                          String bankName, String accountHint,
                          String assignedTo, LocalDate unlockDate) {
        super(assetId, title, "Financial asset", assignedTo, unlockDate);
        this.amount = amount;
        this.bankName = bankName;
        this.accountHint = accountHint;
    }

    @Override
    public String getAssetType() {
        return "Financial";
    }

    @Override
    public String getDisplayInfo() {
        return String.format("💰 Amount: ₹%.2f | Bank: %s | Account Hint: %s",
                amount, bankName, accountHint);
    }

    public double getAmount()       { return amount; }
    public String getBankName()     { return bankName; }
    public String getAccountHint()  { return accountHint; }
    public void setAmount(double amount)         { this.amount = amount; }
    public void setBankName(String bankName)     { this.bankName = bankName; }
    public void setAccountHint(String hint)      { this.accountHint = hint; }
}
