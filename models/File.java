package models;


public abstract class File {
    protected String fileName;
    protected double fileSize;      // current size in MB
    protected final double originalSize; // original size in MB (used for compression limits)

    public File(String fileName, double fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.originalSize = fileSize;
    }

    public String getFileName() { return fileName; }
    public double getFileSize() { return fileSize; }
    public void setFileSize(double fileSize) { this.fileSize = fileSize; }
    public double getOriginalSize() { return originalSize; }

    public abstract void displayInfo();
}

