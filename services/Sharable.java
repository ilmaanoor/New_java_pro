package services;

import models.User;

/**
 * If a File type implements Sharable, it must implement shareFile(User).
 */
public interface Sharable {
    void shareFile(User targetUser);
}

