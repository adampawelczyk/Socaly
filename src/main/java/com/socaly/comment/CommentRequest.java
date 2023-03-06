package com.socaly.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CommentRequest {
    private Long postId;
    private String text;
    private Long parentCommentId;
}
