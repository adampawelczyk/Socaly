package com.socaly.userSettings;

public class UserSettingsNotFoundException extends RuntimeException {
    public UserSettingsNotFoundException(String username) {
        super("User settings not found for user - " + username);
    }
}