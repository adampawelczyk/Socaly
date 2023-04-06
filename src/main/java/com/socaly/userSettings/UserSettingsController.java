package com.socaly.userSettings;

import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/settings")
public class UserSettingsController {
    private final UserSettingsService userSettingsService;

    @GetMapping("/get")
    public UserSettingsResponse getCurrentUserSettings() {
        return userSettingsService.getCurrentUserSettings();
    }

    @PatchMapping("/change/community-content-sort")
    public ResponseEntity<Void> changeCommunityContentSort(@RequestBody Sorting sorting) {
        userSettingsService.changeCommunityContentSort(sorting);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
