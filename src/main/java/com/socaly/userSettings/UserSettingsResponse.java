package com.socaly.userSettings;

import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsResponse {
    private Sorting communityContentSort;
    private boolean openPostsInNewTab;
    private boolean postCommentEmails;
    private boolean commentReplyEmails;
    private boolean postUpVoteEmails;
    private boolean commentUpVoteEmails;
}
