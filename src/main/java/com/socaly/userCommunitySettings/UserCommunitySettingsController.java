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

    @GetMapping("/get/{communityId}")
    public UserCommunitySettingsResponse get(@PathVariable Long communityId) {
        return userCommunitySettingsService.get(communityId);
    }

    @PatchMapping("/update/community-content-sorting/{communityId}")
    public ResponseEntity<Void> updateContentSorting(@PathVariable Long communityId, @RequestBody Sorting sorting) {
        userCommunitySettingsService.updateContentSorting(communityId, sorting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/show-theme/{communityId}")
    public ResponseEntity<Void> updateShowTheme(@PathVariable Long communityId, @RequestBody Boolean showTheme) {
        userCommunitySettingsService.updateShowTheme(communityId, showTheme);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
