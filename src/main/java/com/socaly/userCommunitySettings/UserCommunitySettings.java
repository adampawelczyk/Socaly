package com.socaly.userCommunitySettings;

import com.socaly.util.Sorting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserCommunitySettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long communityId;
    @Enumerated(EnumType.STRING)
    private Sorting CommunityContentSort = Sorting.HOT;
    private Boolean showTheme = true;
}
