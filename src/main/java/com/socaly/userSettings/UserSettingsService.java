package com.socaly.userSettings;

import com.socaly.auth.AuthService;
import com.socaly.user.User;
import com.socaly.user.UserMapper;
import com.socaly.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class UserSettingsService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserSettingsResponse getCurrentUserSettings() {
        User currentUser = authService.getCurrentUser();

        return userRepository.findByUsername(currentUser.getUsername())
                .stream()
                .map(userMapper::mapToUserSettings)
                .findFirst()
                .orElseThrow(
                        () -> new UsernameNotFoundException(currentUser.getUsername())
                );
    }
}
