package com.socaly.community;

public class CommunityNotFoundException extends RuntimeException{
    public CommunityNotFoundException(String message) {
        super(message);
    }
}
