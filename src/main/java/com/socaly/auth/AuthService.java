package com.socaly.auth;

import com.socaly.refreshToken.RefreshTokenRequest;
import com.socaly.image.Image;
import com.socaly.email.EmailService;
import com.socaly.email.EmailVerificationEmail;
import com.socaly.user.User;
import com.socaly.userSettings.UserSettings;
import com.socaly.userSettings.UserSettingsRepository;
import com.socaly.verificationToken.VerificationToken;
import com.socaly.image.ImageRepository;
import com.socaly.refreshToken.RefreshTokenService;
import com.socaly.user.UserRepository;
import com.socaly.verificationToken.VerificationTokenRepository;
import com.socaly.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signUp(final SignUpRequest signUpRequest) {
        final User user = createUserFromSignUpRequest(signUpRequest);
        userRepository.save(user);
        
        final String verificationToken = generateVerificationToken(user);
        sendVerificationEmail(user, verificationToken);
    }

    private User createUserFromSignUpRequest(final SignUpRequest signUpRequest) {
        final User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setCreationDate(Instant.now());
        user.setEmailVerified(false);
        user.setDeleted(false);
        user.setDescription("");

        final UserSettings userSettings = new UserSettings();
        userSettingsRepository.save(userSettings);
        user.setSettings(userSettings);

        Image profileImage = createProfileImage();
        Image profileBanner = createProfileBanner();
        user.setProfileImage(profileImage);
        user.setProfileBanner(profileBanner);

        return user;
    }

    private Image createProfileImage() {
        String imageUrl = generateProfileImage();
        Image profileImage = new Image();
        profileImage.setImageUrl(imageUrl);
        imageRepository.save(profileImage);
        return profileImage;
    }

    private Image createProfileBanner() {
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static%2Fbanner-default.png?alt=media&token=72a29594-4e22-43b6-83de-d93048a90edc";
        Image profileBanner = new Image();
        profileBanner.setImageUrl(imageUrl);
        imageRepository.save(profileBanner);
        return profileBanner;
    }

    private String generateProfileImage() {
        return "https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static%2Favatar-default-"
                + new Random().nextInt(8)
                +".png?alt=media&token=b00daa37-abf9-447d-9a52-80daa478e8ec";
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        return token;
    }

    private void sendVerificationEmail(User user, String token) {
        emailService.sendEmailVerificationEmail(new EmailVerificationEmail(
                "Verify your Socaly email address",
                user.getEmail(),
                user.getUsername(),
                user.getProfileImage().getImageUrl(),
                "http://localhost:8090/api/auth/verify-account/" + token
        ));
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        verificationToken.orElseThrow(() -> new AuthException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User name not found - " + principal.getUsername())
        );
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with name - " + username));

        user.setEmailVerified(true);
        userRepository.save(user);
    }

    public AuthResponse logIn(LogInRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        return AuthResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public Boolean isAuthenticated(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).isAuthenticated();
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());

        return AuthResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
