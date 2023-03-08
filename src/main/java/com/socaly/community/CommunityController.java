package com.socaly.community;

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
    public ResponseEntity<CommunityResponse> createCommunity(@RequestBody CommunityRequest communityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(communityService.save(communityRequest));
    }

    @GetMapping
    public ResponseEntity<List<CommunityResponse>> getAllCommunities() {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<CommunityResponse> getCommunity(@PathVariable String name) {
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
    public ResponseEntity<List<CommunityResponse>> getAllCommunitiesForUser(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunitiesForUser(name));
    }
}
