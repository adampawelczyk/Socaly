package com.socaly.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{username}")
    public UserResponse getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PatchMapping("/change/profile/image")
    public ResponseEntity<Void> changeProfileImage(@RequestBody String imageUrl) {
        userService.changeProfileImage(imageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
