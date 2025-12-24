package models;

import exceptions.StorageLimitExceededException;
import exceptions.FileNotPresentException;
import java.util.ArrayList;

public class User {
    private String userName;
    private ArrayList<File> files = new ArrayList<>();
    private double storageLimit = 100.0; // MB
    private double usedStorage = 0.0;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() { return userName; }

    public void uploadFile(File file) throws StorageLimitExceededException {
        if (usedStorage + file.getFileSize() > storageLimit) {
            throw new StorageLimitExceededException("Storage limit exceeded for " + userName);
        }
        files.add(file);
        usedStorage += file.getFileSize();
        System.out.println(file.getFileName() + " uploaded successfully!");
    }

    public void deleteFile(String fileName) throws FileNotPresentException {
        for (File f : new ArrayList<>(files)) {
            if (f.getFileName().equalsIgnoreCase(fileName)) {
                files.remove(f);
                usedStorage -= f.getFileSize();
                System.out.println(fileName + " deleted successfully!");
                return;
            }
        }
        throw new FileNotPresentException("File '" + fileName + "' not found!");
    }

    public void listFiles() {
        if (files.isEmpty()) {
            System.out.println("No files in storage.");
            return;
        }
        System.out.println("Files of " + userName + ":");
        for (File f : files) f.displayInfo();
        System.out.printf("Used Storage: %.2f MB / %.2f MB%n", usedStorage, storageLimit);
    }

    public File searchFile(String fileName) throws FileNotPresentException {
        for (File f : files) {
            if (f.getFileName().equalsIgnoreCase(fileName)) return f;
        }
        throw new FileNotPresentException("File '" + fileName + "' not found!");
    }

    /**
     * Recalculate usedStorage (call after in-place compressions).
     */
    public void updateStorage() {
        usedStorage = 0.0;
        for (File f : files) usedStorage += f.getFileSize();
    }
}
