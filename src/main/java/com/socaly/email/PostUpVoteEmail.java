package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpVoteEmail {
    private String subject;
    private String recipient;
    private String recipientUsername;
    private String profileImage;
    private String upVotingUserUsername;
    private String upVotingUserProfileImage;
    private String postTitle;
    private String postTimestamp;
    private String postPoints;
    private String communityName;
}
