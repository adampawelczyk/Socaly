package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReplyEmail {
    private String subject;
    private String recipient;
    private String username;
    private String profileImage;
    private String replyingUsername;
    private String replyingUserProfileImage;
    private String postingUsername;
    private String postTitle;
    private String postTimestamp;
    private String communityName;
    private String commentText;
    private String commentTimestamp;
    private String replyText;
}
