package com.socaly.community;

public class CommunityNotFoundException extends RuntimeException{
    public CommunityNotFoundException(String message) {
        super("No community found with name - " + message);
    }
}
