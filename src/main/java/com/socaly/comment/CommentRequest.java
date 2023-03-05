package com.socaly.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CommentRequest {
    private Long id;
    private Long postId;
    private Long parentCommentId;
    private Instant createdDate;
    private String text;
    private String username;
}
