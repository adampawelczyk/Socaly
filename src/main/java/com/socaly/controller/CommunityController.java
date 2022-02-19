package com.socaly.controller;

import com.socaly.dto.CommunityDto;
import com.socaly.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
@Slf4j
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<CommunityDto> createCommunity(@RequestBody CommunityDto communityDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(communityService.save(communityDto));
    }

    @GetMapping
    public ResponseEntity<List<CommunityDto>> getAllCommunities() {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<CommunityDto> getCommunity(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getCommunity(name));
    }

    @GetMapping("/join/{name}")
    public ResponseEntity<Void> joinCommunity(@PathVariable String name) {
        communityService.join(name);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/leave/{name}")
    public ResponseEntity<Void> leaveCommunity(@PathVariable String name) {
        communityService.leave(name);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllCommunitiesForUser/{name}")
    public ResponseEntity<List<CommunityDto>> getAllCommunitiesForUser(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunitiesForUser(name));
    }
}
