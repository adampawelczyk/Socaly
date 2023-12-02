package com.socaly.user;

import com.socaly.auth.AuthService;
import com.socaly.image.Image;
import com.socaly.image.ImageRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final UserMapper userMapper;

    UserResponse get() {
        final User currentUser = authService.getCurrentUser();

        return get(currentUser.getUsername());
    }

    UserResponse get(final String username) {
        final Optional<User> user = userRepository.findByUsername(username);
        
        if (user.isPresent()) {
            return userMapper.mapToDto(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    String getEmail() {
        final User currentUser = authService.getCurrentUser();

        return currentUser.getEmail();
    }

    Boolean isEmailVerified() {
        final User currentUser = authService.getCurrentUser();

        return currentUser.isEmailVerified();
    }

    String getProfileImage(final String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException(username)
        );

        return user.getProfileImage().getImageUrl();
    }

    void changeProfileImage(final String imageUrl) {
        final User currentUser = authService.getCurrentUser();
        currentUser.getProfileImage().setImageUrl(imageUrl);

        imageRepository.save(currentUser.getProfileImage());
    }

    void changeProfileBanner(final String imageUrl) {
        final User currentUser = authService.getCurrentUser();
        currentUser.getProfileBanner().setImageUrl(imageUrl);

        imageRepository.save(currentUser.getProfileBanner());
    }

    void changeDescription(final String description) {
        final User currentUser = authService.getCurrentUser();
        currentUser.setDescription(description);

        userRepository.save(currentUser);
    }

    void changeEmail(final EmailUpdateRequest emailUpdateRequest) {
        final User currentUser = authService.getCurrentUser();

        if (authService.isAuthenticated(currentUser.getUsername(), emailUpdateRequest.getPassword())) {
            currentUser.setEmail(emailUpdateRequest.getEmail());
            currentUser.setEmailVerified(false);

            userRepository.save(currentUser);
        }
    }

    void changePassword(final PasswordUpdateRequest passwordUpdateRequest) {
        final User currentUser = authService.getCurrentUser();

        if (authService.isAuthenticated(currentUser.getUsername(), passwordUpdateRequest.getCurrentPassword())) {
            currentUser.setPassword(authService.encodePassword(passwordUpdateRequest.getNewPassword()));

            userRepository.save(currentUser);
        }
    }

    void delete(final UserDeleteRequest userDeleteRequest) {
        if (isUserAuthenticated(userDeleteRequest)) {
            User currentUser = authService.getCurrentUser();
            
            wipeUserData(currentUser);
            setDeletedUserProfileAndBanner(currentUser);
        }
    }

    private boolean isUserAuthenticated(UserDeleteRequest userDeleteRequest) {
        return authService.isAuthenticated(
                userDeleteRequest.getUsername(),
                userDeleteRequest.getPassword()
        );
    }

    private void wipeUserData(final User user) {
        user.setDeleted(true);
        user.setEmail("");
        user.setEmailVerified(false);
        user.setPassword("");
    }

    private void setDeletedUserProfileAndBanner(final User currentUser) {
        Image profileImage = new Image();
        profileImage.setImageUrl("https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static" +
                "%2Favatar-deleted.png?alt=media&token=7edbd58b-d829-4716-bac2-f80d958027ab");
        imageRepository.save(profileImage);
        currentUser.setProfileImage(profileImage);

        Image profileBanner = new Image();
        profileBanner.setImageUrl("https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static" +
                "%2Fbanner-default.png?alt=media&token=72a29594-4e22-43b6-83de-d93048a90edc");
        imageRepository.save(profileBanner);
        currentUser.setProfileBanner(profileBanner);

        userRepository.save(currentUser);
    }

    boolean isDeleted(final String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        return user.isDeleted();
    }
}
