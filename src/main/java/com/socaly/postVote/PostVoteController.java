package com.socaly.postVote;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/post/vote")
public class PostVoteController {
    private final PostVoteService postVoteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody PostVoteDto postVoteDto) {
        postVoteService.vote(postVoteDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
