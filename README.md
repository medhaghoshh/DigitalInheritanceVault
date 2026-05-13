# 🏛️ Digital Inheritance Vault

> **Secure Your Legacy. Empower Your Heirs.**

A Java OOP console application that lets you store and time-lock digital assets (messages, financial info, documents) for your heirs — assets are automatically revealed on a set future date.

---

## 🌟 USP (Unique Selling Points)

| Feature | Description |
|---|---|
| ⏰ Time-Lock | Assets auto-unlock on a date you set |
| 🔒 Role-based Access | Owners add; Heirs only see unlocked assets |
| 🗂️ 3 Asset Types | Message/Video, Financial, Document |
| 💾 Persistent Storage | Data saved between sessions via serialization |
| 🔐 Password Hashing | SHA-256 — no plain-text passwords |

---

## 📁 Project Structure

```
DigitalInheritanceVault/
├── src/
│   └── com/vault/
│       ├── Main.java
│       ├── model/
│       │   ├── Asset.java          ← Abstract base class
│       │   ├── MessageAsset.java   ← Extends Asset
│       │   ├── FinancialAsset.java ← Extends Asset
│       │   ├── DocumentAsset.java  ← Extends Asset
│       │   └── User.java
│       ├── service/
│       │   └── VaultService.java   ← Business logic
│       ├── ui/
│       │   └── ConsoleUI.java      ← Console interface
│       └── util/
│           ├── FileHandler.java    ← Serialization
│           ├── PasswordUtil.java   ← SHA-256 hashing
│           └── DateUtil.java       ← Date formatting
├── data/                           ← Auto-created at runtime
│   ├── users.dat
│   └── assets.dat
└── README.md
```

---

## 🧠 OOP Concepts Used

| Concept | Where Used |
|---|---|
| **Abstraction** | `Asset` abstract class with abstract methods |
| **Inheritance** | `MessageAsset`, `FinancialAsset`, `DocumentAsset` extend `Asset` |
| **Polymorphism** | `getAssetType()` and `getDisplayInfo()` behave differently per subclass |
| **Encapsulation** | All fields are private with getters/setters |
| **Separation of Concerns** | Model / Service / UI / Util layers |

---

## 🚀 How to Run in VS Code

### Prerequisites
- Java JDK 17 or above
- VS Code with **Extension Pack for Java** installed

### Steps

1. **Clone / Download the project**
   ```bash
   git clone https://github.com/YOUR_USERNAME/DigitalInheritanceVault.git
   cd DigitalInheritanceVault
   ```

2. **Open in VS Code**
   ```bash
   code .
   ```

3. **Run the project**
   - Open `src/com/vault/Main.java`
   - Click the **▶ Run** button (top right) OR press `F5`

4. **Compile and run manually via terminal**
   ```bash
   # From project root
   mkdir -p out
   find src -name "*.java" > sources.txt
   javac -d out @sources.txt
   java -cp out com.vault.Main
   ```

---

## 📖 How to Use

### As an OWNER
1. Register with role `OWNER`
2. Login → Owner Dashboard
3. Add assets (messages, financial info, documents)
4. Set an heir name and unlock date for each asset

### As an HEIR
1. Register with role `HEIR` (use the same name the owner assigned)
2. Login → Heir Dashboard
3. View Inherited Assets — only unlocked ones will appear

---

## 🔧 GitHub Setup (First Time)

```bash
git init
git add .
git commit -m "Initial commit: Digital Inheritance Vault"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/DigitalInheritanceVault.git
git push -u origin main
```

---

## 📄 License
MIT — Free to use for educational purposes.
