package models;

import services.Sharable;
import services.Compressible;
import exceptions.StorageLimitExceededException;


//Document files are sharable and compressible (but only up to a small extent).

public class DocumentFile extends File implements Sharable, Compressible {

    public DocumentFile(String fileName, double fileSize) {
        super(fileName, fileSize);
    }

    
    public void displayInfo() {
        System.out.printf("[Document] %s (%.2f MB)%n", fileName, fileSize);
    }


    public void shareFile(User targetUser) {
        try {
            // Create a copy so the source and target have separate objects
            File copy = new DocumentFile(this.fileName, this.fileSize);
            targetUser.uploadFile(copy);
            System.out.println("Shared document '" + fileName + "' with " + targetUser.getUserName());
        } catch (StorageLimitExceededException e) {
            System.out.println("Cannot share document: " + e.getMessage());
        }
    }

    public void compressFile() {
        // Restriction: documents can be reduced at most 20% of original (min = original * 0.8)
        double minSize = originalSize * 0.8; 
        double candidate = fileSize * 0.5; 
        double newSize = Math.max(candidate, minSize);

        if (newSize >= fileSize) {
            System.out.println("Document '" + fileName + "' cannot be compressed further.");
            return;
        }
        System.out.printf("Document '%s' compressed from %.2f MB to %.2f MB%n", fileName, fileSize, newSize);
        fileSize = newSize;
    }
}
