package com.socaly.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CommentResponse {
    private Long id;
    private Long postId;
    private Long parentCommentId;
    private String timestamp;
    private String editTimestamp;
    private Integer voteCount;
    private String text;
    private String username;
    private boolean upVote;
    private boolean downVote;
}
