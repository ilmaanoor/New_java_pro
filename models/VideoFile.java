package models;

import services.Sharable;
import services.Compressible;
import exceptions.StorageLimitExceededException;

//Video files: sharable and compressible (cannot go below 50% of original; per compress reduces ~30%).
public class VideoFile extends File implements Sharable, Compressible {

    public VideoFile(String fileName, double fileSize) {
        super(fileName, fileSize);
    }

    public void displayInfo() {
        System.out.printf("[Video] %s (%.2f MB)%n", fileName, fileSize);
    }

    public void shareFile(User targetUser) {
        try {
            File copy = new VideoFile(this.fileName, this.fileSize);
            targetUser.uploadFile(copy);
            System.out.println("Shared video '" + fileName + "' with " + targetUser.getUserName());
        } catch (StorageLimitExceededException e) {
            System.out.println("Cannot share video: " + e.getMessage());
        }
    }

    public void compressFile() {
        // Restriction: cannot reduce below 50% of original; each compress reduces to 70% of current size (but not below min)
        double minSize = originalSize * 0.5;
        double newSize = Math.max(fileSize * 0.7, minSize);
        if (newSize >= fileSize) {
            System.out.println("Video '" + fileName + "' cannot be compressed further.");
            return;
        }
        System.out.printf("Video '%s' compressed from %.2f MB to %.2f MB%n", fileName, fileSize, newSize);
        fileSize = newSize;
    }
}
