package com.socaly.user;

import com.socaly.image.Image;
import com.socaly.userCommunitySettings.UserCommunitySettings;
import com.socaly.userSettings.UserSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    private Instant createdDate;
    private boolean isEmailVerified;
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private Image profileImage;

    @OneToOne(fetch = FetchType.LAZY)
    private Image profileBanner;

    @OneToOne(fetch = FetchType.LAZY)
    private UserSettings settings;

    @OneToMany(fetch = FetchType.LAZY)
    private List<UserCommunitySettings> userCommunitySettings;
}
