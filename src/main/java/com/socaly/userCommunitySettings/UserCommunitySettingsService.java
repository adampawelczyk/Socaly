package com.socaly.userCommunitySettings;

import com.socaly.auth.AuthService;
import com.socaly.user.User;
import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserCommunitySettingsService {
    private final AuthService authService;
    private final UserCommunitySettingsMapper userCommunitySettingsMapper;
    private final UserCommunitySettingsRepository userCommunitySettingsRepository;

    UserCommunitySettingsResponse getUserCommunitySettingsById(final Long communityId) {
        final User currentUser = authService.getCurrentUser();

        return currentUser.getUserCommunitySettings()
                .stream()
                .filter(settings -> Objects.equals(settings.getCommunityId(), communityId))
                .map(userCommunitySettingsMapper::mapToUserCommunitySettingsResponse)
                .findFirst()
                .orElseThrow(() -> new UserCommunitySettingsNotFoundException(currentUser.getUsername()));
    }

    void changeCommunityContentSort(final Long communityId, final Sorting sorting) {
        final User currentUser = authService.getCurrentUser();

        UserCommunitySettings userCommunitySettings =currentUser.getUserCommunitySettings()
                .stream()
                .filter(settings -> Objects.equals(settings.getCommunityId(), communityId))
                .findFirst()
                .orElseThrow(() -> new UserCommunitySettingsNotFoundException(currentUser.getUsername()));

        userCommunitySettings.setCommunityContentSort(sorting);
        userCommunitySettingsRepository.save(userCommunitySettings);
    }

    void changeShowTheme(final Long communityId, final boolean showTheme) {
        User currentUser = authService.getCurrentUser();

        UserCommunitySettings userCommunitySettings =currentUser.getUserCommunitySettings()
                .stream()
                .filter(settings -> Objects.equals(settings.getCommunityId(), communityId))
                .findFirst()
                .orElseThrow(() -> new UserCommunitySettingsNotFoundException(currentUser.getUsername()));

        userCommunitySettings.setShowTheme(showTheme);
        userCommunitySettingsRepository.save(userCommunitySettings);
    }
}
