package com.socaly.dto;

import com.socaly.entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVoteDto {
    private VoteType voteType;
    private Long commentID;
}
