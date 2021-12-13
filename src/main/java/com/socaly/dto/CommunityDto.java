package com.socaly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {
    private Long id;
    private String name;
    private String description;
    private Instant createdDate;
    private Integer numberOfPosts;
}
