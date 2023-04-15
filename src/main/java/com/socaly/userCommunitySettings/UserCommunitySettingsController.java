package com.socaly.userCommunitySettings;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/community/settings")
public class UserCommunitySettingsController {
    private final UserCommunitySettingsService userCommunitySettingsService;

    @GetMapping("/get/{communityId}")
    public UserCommunitySettingsResponse getUserCommunitySettingsById(@PathVariable Long communityId) {
        return userCommunitySettingsService.getUserCommunitySettingsById(communityId);
    }
}
