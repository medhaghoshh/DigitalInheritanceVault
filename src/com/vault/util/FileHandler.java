package com.vault.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

// OOP: Utility class for file-based data persistence
public class FileHandler {

    public static void save(Object data, String filePath) {
        try {
            // Create directory if it doesn't exist
            Files.createDirectories(Paths.get(filePath).getParent());
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(data);
            }
        } catch (IOException e) {
            System.out.println("⚠️  Error saving data: " + e.getMessage());
        }
    }

    public static Object load(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️  Error loading data: " + e.getMessage());
            return null;
        }
    }
}
