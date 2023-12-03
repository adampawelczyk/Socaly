package com.socaly.userCommunitySettings;

import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/community/settings")
public class UserCommunitySettingsController {
    private final UserCommunitySettingsService userCommunitySettingsService;

    @GetMapping("/get/{id}")
    public UserCommunitySettingsResponse get(@PathVariable Long id) {
        return userCommunitySettingsService.get(id);
    }

    @PatchMapping("/update/community-content-sorting/{id}")
    public ResponseEntity<Void> updateContentSorting(@PathVariable Long id, @RequestBody Sorting sorting) {
        userCommunitySettingsService.updateContentSorting(id, sorting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/show-theme/{id}")
    public ResponseEntity<Void> updateShowTheme(@PathVariable Long id, @RequestBody Boolean showTheme) {
        userCommunitySettingsService.updateShowTheme(id, showTheme);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
