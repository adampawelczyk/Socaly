package com.socaly.userSettings;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/settings")
public class UserSettingsController {
    private final UserSettingsService userSettingsService;

    @GetMapping("/get")
    public UserSettingsResponse getCurrentUserSettings() {
        return userSettingsService.getCurrentUserSettings();
    }
}
