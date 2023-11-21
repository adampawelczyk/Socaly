package com.socaly.user;

import com.socaly.auth.AuthService;
import com.socaly.image.Image;
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
        final User currentUser = authService.getCurrentUser();

        return getUser(currentUser.getUsername());
    }

    public UserResponse getUser(final String username) {
        return userRepository.findByUsername(username)
                .stream()
                .map(userMapper::mapToDto)
                .findFirst()
                .orElseThrow(
                    () -> new UsernameNotFoundException(username)
                );
    }

    public String getCurrentUserEmail() {
        final User currentUser = authService.getCurrentUser();

        return currentUser.getEmail();
    }

    public Boolean isEmailVerified() {
        final User currentUser = authService.getCurrentUser();

        return currentUser.isEmailVerified();
    }

    public String getUserProfileImage(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException(username)
            );

        return user.getProfileImage().getImageUrl();
    }

    void updateProfileImage(String imageUrl) {
        User currentUser = authService.getCurrentUser();
        currentUser.getProfileImage().setImageUrl(imageUrl);

        imageRepository.save(currentUser.getProfileImage());
    }

    void updateProfileBanner(String imageUrl) {
        User currentUser = authService.getCurrentUser();
        currentUser.getProfileBanner().setImageUrl(imageUrl);

        imageRepository.save(currentUser.getProfileBanner());
    }

    void updateDescription(String description) {
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

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User currentUser = authService.getCurrentUser();

        if (authService.isAuthenticated(currentUser.getUsername(), passwordUpdateRequest.getCurrentPassword())) {
            currentUser.setPassword(authService.encodePassword(passwordUpdateRequest.getNewPassword()));

            userRepository.save(currentUser);
        }
    }

    void delete(UserDeleteRequest userDeleteRequest) {
        if (authService.isAuthenticated(userDeleteRequest.getUsername(), userDeleteRequest.getPassword())) {
            User currentUser = authService.getCurrentUser();

            currentUser.setDeleted(true);
            currentUser.setEmail("");
            currentUser.setEmailVerified(false);
            currentUser.setPassword("");

            Image profileImage = new Image();
            profileImage.setImageUrl("https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static" +
                    "%2Favatar-deleted.png?alt=media&token=7edbd58b-d829-4716-bac2-f80d958027ab");
            imageRepository.save(profileImage);

            currentUser.setProfileImage(profileImage);

            userRepository.save(currentUser);

            Image profileBanner = new Image();
            profileBanner.setImageUrl("https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static" +
                    "%2Fbanner-default.png?alt=media&token=72a29594-4e22-43b6-83de-d93048a90edc");
            imageRepository.save(profileBanner);

            currentUser.setProfileBanner(profileBanner);
        }
    }

    boolean isDeleted(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        return user.isDeleted();
    }
}
