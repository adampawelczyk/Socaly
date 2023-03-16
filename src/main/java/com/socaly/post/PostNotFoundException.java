package com.socaly.post;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super("Post not found with id - " + message);
    }
}
