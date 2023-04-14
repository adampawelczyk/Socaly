package com.socaly.userCommunitySettings;

public class UserCommunitySettingsNotFoundException extends RuntimeException {
    public UserCommunitySettingsNotFoundException(String username) {
        super("User community settings not found for user - " + username);
    }
}
