package com.socaly.userSettings;

import com.socaly.auth.AuthService;
import com.socaly.user.User;
import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class UserSettingsService {
    private final AuthService authService;
    private final UserSettingsRepository userSettingsRepository;
    private final UserSettingsMapper userSettingsMapper;

    UserSettingsResponse getCurrentUserSettings() {
        User currentUser = authService.getCurrentUser();

        return userSettingsRepository.findById(currentUser.getSettings().getId())
                .stream()
                .map(userSettingsMapper::mapToUserSettingsResponse)
                .findFirst()
                .orElseThrow(
                        () -> new UserSettingsNotFoundException(currentUser.getUsername())
                );
    }

    void updateCommunityContentSort(Sorting sorting) {
        User currentUser = authService.getCurrentUser();

        UserSettings userSettings = currentUser.getSettings();
        userSettings.setCommunityContentSort(sorting);
        userSettingsRepository.save(userSettings);
    }

    void updateOpenPostsInNewTab(boolean openPostsInNewTab) {
        User currentUser = authService.getCurrentUser();

        UserSettings userSettings = currentUser.getSettings();
        userSettings.setOpenPostsInNewTab(openPostsInNewTab);
        userSettingsRepository.save(userSettings);
    }

    void updatePostCommentEmails(boolean postCommentEmails) {
        User currentUser = authService.getCurrentUser();

        UserSettings userSettings = currentUser.getSettings();
        userSettings.setPostCommentEmails(postCommentEmails);
        userSettingsRepository.save(userSettings);
    }

    void updateCommentReplyEmails(boolean commentReplyEmails) {
        User currentUser = authService.getCurrentUser();

        UserSettings userSettings = currentUser.getSettings();
        userSettings.setCommentReplyEmails(commentReplyEmails);
        userSettingsRepository.save(userSettings);
    }

    void changePostUpVoteEmails(boolean postUpvoteEmails) {
        User currentUser = authService.getCurrentUser();

        UserSettings userSettings = currentUser.getSettings();
        userSettings.setPostUpVoteEmails(postUpvoteEmails);
        userSettingsRepository.save(userSettings);
    }

    void changeCommentUpVoteEmails(boolean commentUpVoteEmails) {
        User currentUser = authService.getCurrentUser();

        UserSettings userSettings = currentUser.getSettings();
        userSettings.setCommentUpVoteEmails(commentUpVoteEmails);
        userSettingsRepository.save(userSettings);
    }
}
