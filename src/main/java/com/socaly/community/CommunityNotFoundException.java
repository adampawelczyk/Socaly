package com.socaly.community;

public class CommunityNotFoundException extends RuntimeException{
    public CommunityNotFoundException(String name) {
        super("No community found with name - " + name);
    }
}
