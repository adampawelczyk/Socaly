package com.socaly.comment;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String id) {
        super("Comment not found with id - " + id);
    }
}
