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
    public ResponseEntity<CommunityResponse> create(@RequestBody CommunityRequest communityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(communityService.create(communityRequest));
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<CommunityResponse> get(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.findCommunityByName(name));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<CommunityResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAll());
    }

    @GetMapping("/get/all/by-user/{username}")
    public ResponseEntity<List<CommunityResponse>> getAllByUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllByUser(username));
    }

    @PostMapping("/join/{communityName}")
    public ResponseEntity<Void> join(@PathVariable String communityName) {
        communityService.join(communityName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/leave/{communityName}")
    public ResponseEntity<Void> leave(@PathVariable String communityName) {
        communityService.leave(communityName);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
