package com.socaly.commentVote;

import com.socaly.util.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVoteDto {
    private VoteType voteType;
    private Long commentId;
}
