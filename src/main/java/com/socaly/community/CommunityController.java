package com.socaly.community;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping("/create")
    public ResponseEntity<CommunityResponse> createCommunity(@RequestBody CommunityRequest communityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(communityService.saveCommunity(communityRequest));
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<CommunityResponse> getCommunity(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.findCommunityByName(name));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<CommunityResponse>> getAllCommunities() {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunities());
    }

    @GetMapping("/get/all/by-user/{name}")
    public ResponseEntity<List<CommunityResponse>> getAllCommunitiesForUser(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunitiesForUser(name));
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
}
