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

    @PatchMapping("/change/community-content-sort")
    public ResponseEntity<Void> changeCommunityContentSort(@RequestBody Sorting sorting) {
        userSettingsService.changeCommunityContentSort(sorting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/open-posts-in-new-tab")
    public ResponseEntity<Void> changeOpenPostsInNewTab(@RequestBody Boolean openPostsInNewTab) {
        userSettingsService.changeOpenPostsInNewTab(openPostsInNewTab);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/post-comment-emails")
    public ResponseEntity<Void> changePostCommentEmails(@RequestBody Boolean postCommentEmails) {
        userSettingsService.changePostCommentEmails(postCommentEmails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/comment-reply-emails")
    public ResponseEntity<Void> changeCommentReplyEmails(@RequestBody Boolean commentReplyEmails) {
        userSettingsService.changeCommentReplyEmails(commentReplyEmails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/post-up-vote-emails")
    public ResponseEntity<Void> changePostUpVoteEmails(@RequestBody Boolean postUpvoteEmails) {
        userSettingsService.changePostUpVoteEmails(postUpvoteEmails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/comment-up-vote-emails")
    public ResponseEntity<Void> changeCommentUpVoteEmails(@RequestBody Boolean commentUpVoteEmails) {
        userSettingsService.changeCommentUpVoteEmails(commentUpVoteEmails);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
