package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyUpVoteEmail {
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
    private String commentUsername;
    private String commentTimeSinceCreation;
    private String commentText;
    private String commentPoints;
    private String commentReplyCount;
    private String replyTimeSinceCreation;
    private String replyText;
    private String replyPoints;
    private String replyReplyCount;
    private String upVoteUsername;
    private String upVoteUserProfileImage;
}
