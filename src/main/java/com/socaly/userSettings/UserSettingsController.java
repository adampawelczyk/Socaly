package com.socaly.userSettings;

import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/settings")
public class UserSettingsController {
    private final UserSettingsService userSettingsService;

    @GetMapping("/get")
    public UserSettingsResponse getCurrentUserSettings() {
        return userSettingsService.getCurrentUserSettings();
    }

    @PatchMapping("/update/community-content-sort")
    public ResponseEntity<Void> updateCommunityContentSort(@RequestBody Sorting sorting) {
        userSettingsService.updateCommunityContentSort(sorting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/open-posts-in-new-tab")
    public ResponseEntity<Void> updateOpenPostsInNewTab(@RequestBody Boolean openPostsInNewTab) {
        userSettingsService.updateOpenPostsInNewTab(openPostsInNewTab);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/post-comment-emails")
    public ResponseEntity<Void> updatePostCommentEmails(@RequestBody Boolean postCommentEmails) {
        userSettingsService.changePostCommentEmails(postCommentEmails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/comment-reply-emails")
    public ResponseEntity<Void> changeCommentReplyEmails(@RequestBody Boolean commentReplyEmails) {
        userSettingsService.changeCommentReplyEmails(commentReplyEmails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/post-up-vote-emails")
    public ResponseEntity<Void> changePostUpVoteEmails(@RequestBody Boolean postUpvoteEmails) {
        userSettingsService.changePostUpVoteEmails(postUpvoteEmails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/comment-up-vote-emails")
    public ResponseEntity<Void> changeCommentUpVoteEmails(@RequestBody Boolean commentUpVoteEmails) {
        userSettingsService.changeCommentUpVoteEmails(commentUpVoteEmails);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
