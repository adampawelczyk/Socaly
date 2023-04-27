package com.socaly.user;

import com.socaly.auth.AuthService;
import com.socaly.image.ImageRepository;
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

    public UserResponse getCurrentUser() {
        User user = authService.getCurrentUser();

        return getUser(user.getUsername());
    }

    public String getCurrentUserEmail() {
        User user = authService.getCurrentUser();

        return user.getEmail();
    }

    public Boolean isEmailVerified() {
        User user = authService.getCurrentUser();

        return user.isEmailVerified();
    }

    public UserResponse getUser(String username) {
        return userRepository.findByUsername(username)
                .stream()
                .map(userMapper::mapToDto)
                .findFirst()
                .orElseThrow(
                    () -> new UsernameNotFoundException(username)
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

    void updateEmail(EmailUpdateRequest emailUpdateRequest) {
        User currentUser = authService.getCurrentUser();

        if (authService.isAuthenticated(currentUser.getUsername(), emailUpdateRequest.getPassword())) {
            currentUser.setEmail(emailUpdateRequest.getEmail());
            currentUser.setEmailVerified(false);

            userRepository.save(currentUser);
        }
    }
}
