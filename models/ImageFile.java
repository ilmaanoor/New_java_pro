package models;

import services.Sharable;
import services.Compressible;
import exceptions.StorageLimitExceededException;

//Image files: sharable and compressible (can go down to 50% of original).

public class ImageFile extends File implements Sharable, Compressible {

    public ImageFile(String fileName, double fileSize) {
        super(fileName, fileSize);
    }

    public void displayInfo() {
        System.out.printf("[Image] %s (%.2f MB)%n", fileName, fileSize);
    }

    public void shareFile(User targetUser) {
        try {
            File copy = new ImageFile(this.fileName, this.fileSize);
            targetUser.uploadFile(copy);
            System.out.println("Shared image '" + fileName + "' with " + targetUser.getUserName());
        } catch (StorageLimitExceededException e) {
            System.out.println("Cannot share image: " + e.getMessage());
        }
    }

    public void compressFile() {
        // Restriction: images cannot be compressed below 50% of original
        double minSize = originalSize * 0.5;
        double newSize = Math.max(fileSize * 0.5, minSize);
        if (newSize >= fileSize) {
            System.out.println("Image '" + fileName + "' cannot be compressed further.");
            return;
        }
        System.out.printf("Image '%s' compressed from %.2f MB to %.2f MB%n", fileName, fileSize, newSize);
        fileSize = newSize;
    }
}
