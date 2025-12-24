package main;

import java.util.*;
import models.*;
import services.*;
import exceptions.*;

public class CloudStorageSimulator {
    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Cloud Storage Simulator ===");

    
        users.add(new User("Ilmaa"));
        users.add(new User("Noor"));

        while (true) {
            System.out.println("\n1. Create User");
            System.out.println("2. Upload File");
            System.out.println("3. Delete File");
            System.out.println("4. List Files");
            System.out.println("5. Search File");
            System.out.println("6. Share File");
            System.out.println("7. Compress File");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            String in = sc.nextLine();
            int choice;
            try { choice = Integer.parseInt(in); }
            catch (NumberFormatException e) { System.out.println("Invalid input"); continue; }

            try {
                switch (choice) {
                    case 1 :
                    {
                        createUser();
                        break;
                    } 
                    case 2:
                    {
                        uploadFile();
                        break;
                    }
                    case 3:
                    {
                        deleteFile();
                        break;
                    }
                    case 4:
                    {
                        listFiles();
                        break;
                    }
                    case 5:
                    {
                        searchFile();
                        break;
                    }
                    case 6:
                    {
                        shareFile();
                        break;
                    }
                    case 7:
                    {
                        compressFile();
                        break;
                    }
                    case 8:
                    { 
                        System.out.println("Exiting... Goodbye!"); 
                        return; 
                    }
                    default:
                    {
                        System.out.println("Invalid choice!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void createUser() {
        System.out.print("Enter username: ");
        String name = sc.nextLine();
        users.add(new User(name));
        System.out.println("User '" + name + "' created!");
    }

    private static User findUser(String name) {
        for (User u : users) if (u.getUserName().equalsIgnoreCase(name)) return u;
        return null;
    }

    private static User getUser() {
        System.out.print("Enter username: ");
        String name = sc.nextLine();
        User u = findUser(name);
        if (u == null) System.out.println("User not found!");
        return u;
    }

    private static void uploadFile() throws StorageLimitExceededException {
        User u = getUser();
        if (u == null) return;

        System.out.print("Enter file type (doc/img/video): ");
        String type = sc.nextLine();
        System.out.print("Enter file name: ");
        String name = sc.nextLine();
        System.out.print("Enter file size (MB): ");
        double size;
        try { size = Double.parseDouble(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Invalid size."); return; }

        File f;
        switch (type.toLowerCase()) {
            case "doc" -> f = new DocumentFile(name, size);
            case "img", "image" -> f = new ImageFile(name, size);
            case "video" -> f = new VideoFile(name, size);
            default -> { System.out.println("Invalid type!"); return; }
        }

        u.uploadFile(f);
    }

    private static void deleteFile() throws FileNotPresentException {
        User u = getUser();
        if (u == null) return;
        System.out.print("Enter file name to delete: ");
        String name = sc.nextLine();
        u.deleteFile(name);
    }

    private static void listFiles() {
        User u = getUser();
        if (u == null) return;
        u.listFiles();
    }

    private static void searchFile() throws FileNotPresentException {
        User u = getUser();
        if (u == null) return;
        System.out.print("Enter file name to search: ");
        String name = sc.nextLine();
        File f = u.searchFile(name);
        System.out.println("File found:");
        f.displayInfo();
    }

    private static void shareFile() throws FileNotPresentException {
        System.out.print("Enter source username: ");
        String sName = sc.nextLine();
        User source = findUser(sName);
        if (source == null) { System.out.println("Source user not found!"); return; }

        System.out.print("Enter file name to share: ");
        String fileName = sc.nextLine();
        File f = source.searchFile(fileName); // may throw FileNotPresentException

        System.out.print("Enter target username: ");
        String targetName = sc.nextLine();
        User target = findUser(targetName);
        if (target == null) { System.out.println("Target user not found!"); return; }

        if (f instanceof Sharable) {
            
            ((Sharable) f).shareFile(target);
        } else {
            System.out.println("This file type cannot be shared.");
        }
    }

    private static void compressFile() throws FileNotPresentException {
        User u = getUser();
        if (u == null) return;
        System.out.print("Enter file name to compress: ");
        String name = sc.nextLine();
        File f = u.searchFile(name); 

        if (f instanceof Compressible) {
            ((Compressible) f).compressFile();
            // update user's used storage since file's size changed
            u.updateStorage();
        } else {
            System.out.println("This file type cannot be compressed.");
        }
    }
}
