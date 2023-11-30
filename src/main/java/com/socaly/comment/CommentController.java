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
        commentService.create(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get/{commentId}")
    public ResponseEntity<CommentResponse> get(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.get(commentId));
    }

    @GetMapping("get/all/by-post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getAllByPost(postId));
    }

    @GetMapping("get/all/by-user/{username}")
    public ResponseEntity<List<CommentResponse>> getAllByUser(@PathVariable String username) {
        return ResponseEntity.ok(commentService.getAllByUser(username));
    }

    @GetMapping("get/sub-comments/{commentId}")
    public ResponseEntity<List<CommentResponse>> getSubComments(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getSubComments(commentId));
    }

    @PatchMapping("/edit/{commentId}")
    public ResponseEntity<Void> edit(@PathVariable Long commentId, @RequestBody String text) {
        commentService.edit(commentId, text);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
