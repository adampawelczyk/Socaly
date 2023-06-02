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
    private String recipientProfileImage;
    private String communityName;
    private String postTimestamp;
    private String postTitle;
    private String commentUsername;
    private String commentUserProfileImage;
    private String commentText;
}
