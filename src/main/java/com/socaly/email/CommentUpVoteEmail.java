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
    private String postTimestamp;
    private String postTitle;
    private String postPoints;
    private String commentCount;
    private String commentTimestamp;
    private String commentText;
    private String upVoteUsername;
    private String upVoteUserProfileImage;
}
