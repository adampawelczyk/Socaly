package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentReplyEmail {
    private String subject;
    private String recipientEmail;
    private String recipientUsername;
    private String recipientProfileImage;
    private String communityName;
    private String postId;
    private String postUsername;
    private String postTimeSinceCreation;
    private String postTitle;
    private String postPoints;
    private String commentCount;
    private String commentTimeSinceCreation;
    private String commentText;
    private String commentPoints;
    private String commentReplyCount;
    private String replyId;
    private String replyUsername;
    private String replyUserProfileImage;
    private String replyText;
}
