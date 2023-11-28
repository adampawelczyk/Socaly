package com.socaly.comment;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest commentRequest) {
        commentService.saveComment(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    @GetMapping("get/all/by-post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("get/all/by-user/{username}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForUser(@PathVariable String username) {
        return ResponseEntity.ok(commentService.getAllCommentsForUser(username));
    }

    @GetMapping("get/all/sub-comments/{commentId}")
    public ResponseEntity<List<CommentResponse>> getAllSubCommentsForComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getSubCommentsForComment(commentId));
    }

    @PostMapping("/edit/{commentId}")
    public ResponseEntity<Void> editComment(@PathVariable Long commentId, @RequestBody String text) {
        commentService.edit(commentId, text);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
