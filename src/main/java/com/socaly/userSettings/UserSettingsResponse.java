package com.socaly.userSettings;

import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsResponse {
    private String createdDate;
    private String profileImage;
    private String profileBanner;
    private String description;
    private String email;
    private boolean isEmailVerified;
    private Sorting communityContentSort;
    private boolean openPostsInNewTab;
    private boolean postCommentEmails;
    private boolean commentReplyEmails;
    private boolean postUpvoteEmails;
    private boolean commentUpVoteEmails;
}
