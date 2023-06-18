package com.socaly.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String creationDate;
    private String profileImage;
    private String profileBanner;
    private String description;
}
