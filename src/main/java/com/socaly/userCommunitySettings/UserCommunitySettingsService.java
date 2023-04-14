package com.socaly.userCommunitySettings;

import com.socaly.auth.AuthService;
import com.socaly.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserCommunitySettingsService {
    private final AuthService authService;
    private final UserCommunitySettingsMapper userCommunitySettingsMapper;

    UserCommunitySettingsResponse getUserCommunitySettingsById(Long communityId) {
        User currentUser = authService.getCurrentUser();

        return currentUser.getUserCommunitySettings()
                .stream()
                .filter(settings -> Objects.equals(settings.getCommunityId(), communityId))
                .map(userCommunitySettingsMapper::mapToUserCommunitySettingsResponse)
                .findFirst()
                .orElseThrow(() -> new UserCommunitySettingsNotFoundException(currentUser.getUsername()));
    }
}
