package com.socaly.auth;

import com.socaly.refreshToken.RefreshTokenRequest;
import com.socaly.image.Image;
import com.socaly.email.EmailService;
import com.socaly.email.NotificationEmail;
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
    public void signUp(SignUpRequest signUpRequest) {
        User user = new User();

        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setCreatedDate(Instant.now());
        user.setEmailVerified(false);
        user.setDescription("");

        UserSettings settings = new UserSettings();
        userSettingsRepository.save(settings);

        user.setSettings(settings);

        Image profileImage = new Image();
        profileImage.setImageUrl(generateProfileImage());
        imageRepository.save(profileImage);

        user.setProfileImage(profileImage);

        Image profileBanner = new Image();
        profileBanner.setImageUrl("https://firebasestorage.googleapis.com/v0/b/socaly-eb5f5.appspot.com/o/static%2F" +
                "banner-default.png?alt=media&token=72a29594-4e22-43b6-83de-d93048a90edc");
        imageRepository.save(profileBanner);

        user.setProfileBanner(profileBanner);

        userRepository.save(user);
        
        String token = generateVerificationToken(user);
        emailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),
                "Thank you for signing up to Socaly, please click on the below url to activate your account: " +
                        "http://localhost:8090/api/auth/verify-account/" + token));
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
