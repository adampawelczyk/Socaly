package com.socaly.comment;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super("Comment not found with id - " + message);
    }
}
