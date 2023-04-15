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
    public UserCommunitySettingsResponse getUserCommunitySettingsById(@PathVariable Long communityId) {
        return userCommunitySettingsService.getUserCommunitySettingsById(communityId);
    }

    @PatchMapping("/change/community-content-sort/{communityId}")
    public ResponseEntity<Void> changeCommunityContentSort(@PathVariable Long communityId, @RequestBody Sorting sorting) {
        userCommunitySettingsService.changeCommunityContentSort(communityId, sorting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/show-theme/{communityId}")
    public ResponseEntity<Void> changeShowTheme(@PathVariable Long communityId, @RequestBody Boolean showTheme) {
        userCommunitySettingsService.changeShowTheme(communityId, showTheme);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
