package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentUpVoteEmail {
    private String subject;
    private String recipient;
    private String recipientUsername;
    private String recipientProfileImage;
    private String communityName;
    private String postUsername;
    private String postTimeSinceCreation;
    private String postTitle;
    private String postPoints;
    private String commentCount;
    private String commentTimeSinceCreation;
    private String commentText;
    private String commentPoints;
    private String commentReplyCount;
    private String upVoteUsername;
    private String upVoteUserProfileImage;
}
