package com.socaly.commentVote;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/comments/vote")
public class CommentVoteController {
    private final CommentVoteService commentVoteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody CommentVoteDto commentVoteDto) {
        commentVoteService.vote(commentVoteDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
