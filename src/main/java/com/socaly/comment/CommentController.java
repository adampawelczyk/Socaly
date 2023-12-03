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

    @GetMapping("/get/{id}")
    public ResponseEntity<CommentResponse> get(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.get(commentId));
    }

    @GetMapping("get/all/by-post/{id}")
    public ResponseEntity<List<CommentResponse>> getAllByPost(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getAllByPost(id));
    }

    @GetMapping("get/all/by-user/{name}")
    public ResponseEntity<List<CommentResponse>> getAllByUser(@PathVariable String name) {
        return ResponseEntity.ok(commentService.getAllByUser(name));
    }

    @GetMapping("get/sub-comments/{id}")
    public ResponseEntity<List<CommentResponse>> getSubComments(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getSubComments(id));
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Void> edit(@PathVariable Long id, @RequestBody String text) {
        commentService.edit(id, text);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
