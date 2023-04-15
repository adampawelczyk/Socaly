package com.socaly.userSettings;

import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Sorting communityContentSort = Sorting.HOT;
    private Boolean rememberLastCommunityContentSort = false;
    private Boolean useCustomCommunityThemes = true;
    private Boolean openPostsInNewTab = false;
    private Boolean postCommentEmails = true;
    private Boolean commentReplyEmails = true;
    private Boolean postUpVoteEmails = true;
    private Boolean commentUpVoteEmails = true;
}
