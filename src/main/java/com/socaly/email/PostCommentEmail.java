package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentEmail {
    private String subject;
    private String recipientEmail;
    private String recipientUsername;
    private String recipientUserProfileImage;
    private String commentUsername;
    private String commentUserProfileImage;
    private String postTitle;
    private String postTimestamp;
    private String communityName;
    private String commentText;
}
