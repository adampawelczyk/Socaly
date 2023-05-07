package com.socaly.userCommunitySettings;

import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommunitySettingsResponse {
    private Sorting communityContentSort;
    private Boolean showTheme;
}
