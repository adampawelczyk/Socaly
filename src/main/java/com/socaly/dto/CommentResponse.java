package com.socaly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private Long postId;
    private Long parentCommentId;
    private Instant createdDate;
    private Integer voteCount;
    private String text;
    private String username;
    private boolean upVote;
    private boolean downVote;
}
