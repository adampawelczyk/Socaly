package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCommentEmail {
    private String subject;
    private String recipientEmail;
    private String recipientUsername;
    private String recipientProfileImage;
    private String communityName;
    private String postTimeSinceCreation;
    private String postTitle;
    private String postPoints;
    private String commentCount;
    private String commentUsername;
    private String commentUserProfileImage;
    private String commentText;
}
