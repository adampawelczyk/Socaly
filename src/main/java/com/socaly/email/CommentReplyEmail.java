package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReplyEmail {
    private String subject;
    private String recipientEmail;
    private String recipientUsername;
    private String recipientProfileImage;
    private String communityName;
    private String postUsername;
    private String postTimestamp;
    private String postTitle;
    private String commentTimestamp;
    private String commentText;
    private String replyUsername;
    private String replyUserProfileImage;
    private String replyText;
}
