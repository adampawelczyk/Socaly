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
    private String title;
    private String description;
    private List<String> images;
    private String username;
    private String communityName;
    private Integer points;
    private Integer commentCount;
    private String timeSinceCreation;
    private boolean upVote;
    private boolean downVote;
}
