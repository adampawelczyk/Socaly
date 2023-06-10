package com.socaly.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postTitle;
    private String description;
    private List<String> images;
    private String username;
    private String communityName;
    private Integer voteCount;
    private Integer commentCount;
    private String timestamp;
    private boolean upVote;
    private boolean downVote;
}
