package com.socaly.user;

import com.socaly.auth.AuthService;
import com.socaly.image.ImageRepository;
import com.socaly.userSettings.UserSettingsResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final UserMapper userMapper;

    public UserResponse getUser(String username) {
        return userRepository.findByUsername(username)
                .stream()
                .map(userMapper::mapToDto)
                .findFirst()
                .orElseThrow(
                    () -> new UsernameNotFoundException(username)
                );
    }

    public UserSettingsResponse getUserSettings() {
        User currentUser = authService.getCurrentUser();

        return userRepository.findByUsername(currentUser.getUsername())
                .stream()
                .map(userMapper::mapToUserSettings)
                .findFirst()
                .orElseThrow(
                        () -> new UsernameNotFoundException(currentUser.getUsername())
                );
    }

    void changeProfileImage(String imageUrl) {
        User currentUser = authService.getCurrentUser();
        currentUser.getProfileImage().setImageUrl(imageUrl);

        imageRepository.save(currentUser.getProfileImage());
    }

    void changeProfileBanner(String imageUrl) {
        User currentUser = authService.getCurrentUser();
        currentUser.getProfileBanner().setImageUrl(imageUrl);

        imageRepository.save(currentUser.getProfileBanner());
    }

    void changeDescription(String description) {
        User currentUser = authService.getCurrentUser();
        currentUser.setDescription(description);

        userRepository.save(currentUser);
    }
}
