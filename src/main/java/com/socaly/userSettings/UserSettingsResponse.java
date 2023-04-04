package com.socaly.userSettings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsResponse {
    private String createdDate;
    private String profileImage;
    private String profileBanner;
    private String description;
    private String email;
    private boolean isEmailVerified;
}
