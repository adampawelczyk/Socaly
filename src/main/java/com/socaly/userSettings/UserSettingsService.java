package com.socaly.userSettings;

import com.socaly.auth.AuthService;
import com.socaly.user.User;
import com.socaly.user.UserMapper;
import com.socaly.user.UserRepository;
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
}
