package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpVoteEmail {
    private String subject;
    private String recipient;
    private String recipientUsername;
    private String recipientProfileImage;
    private String communityName;
    private String postTimeSinceCreation;
    private String postTitle;
    private String postPoints;
    private String commentCount;
    private String upVoteUsername;
    private String upVoteUserProfileImage;
}
