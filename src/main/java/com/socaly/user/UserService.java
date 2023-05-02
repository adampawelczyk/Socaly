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
            profileImage.setImageUrl("https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static%2Favatar-deleted.png?alt=media&token=c2a1cb63-5a7a-4280-a4f9-c415fdf0bc7e");
            imageRepository.save(profileImage);

            currentUser.setProfileImage(profileImage);

            userRepository.save(currentUser);

            Image profileBanner = new Image();
            profileBanner.setImageUrl("https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static%2F" +
                    "banner-default.png?alt=media&token=72a29594-4e22-43b6-83de-d93048a90edc");
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
