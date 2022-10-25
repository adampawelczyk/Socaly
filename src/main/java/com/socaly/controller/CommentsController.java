package com.socaly.controller;

import com.socaly.dto.CommentDto;
import com.socaly.dto.CommentResponse;
import com.socaly.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
        commentService.save(commentDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/edit/{commentId}")
    public ResponseEntity<Void> editComment(@PathVariable Long commentId, @RequestBody String text) {
        commentService.edit(commentId, text);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(commentId));
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
    }

    @GetMapping("/subcomments/{commentId}")
    public ResponseEntity<List<CommentResponse>> getAllSubCommentsForComment(@PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getSubCommentsForComment(commentId));
    }
}
