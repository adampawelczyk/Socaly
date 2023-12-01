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

    @GetMapping("/get")
    public UserResponse get() {
        return userService.get();
    }

    @GetMapping("/get/{username}")
    public UserResponse get(@PathVariable String username) {
        return userService.get(username);
    }

    @GetMapping("/get/email")
    public String getEmail() {
        return userService.getEmail();
    }

    @GetMapping("/is-email-verified")
    public Boolean isEmailVerified() {
        return userService.isEmailVerified();
    }

    @GetMapping("/get/profile/image/{username}")
    public String getProfileImage(@PathVariable String username) {
        return userService.getProfileImage(username);
    }

    @PatchMapping("/change/profile/image")
    public ResponseEntity<Void> changeProfileImage(@RequestBody String imageUrl) {
        userService.changeProfileImage(imageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/profile/banner")
    public ResponseEntity<Void> changeProfileBanner(@RequestBody String imageUrl) {
        userService.changeProfileBanner(imageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/description")
    public ResponseEntity<Void> changeDescription(@RequestBody(required = false) String description) {
        userService.changeDescription(description);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/email")
    public ResponseEntity<Void> changeEmail(@RequestBody EmailUpdateRequest emailUpdateRequest) {
        userService.changeEmail(emailUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(passwordUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody UserDeleteRequest userDeleteRequest) {
        userService.delete(userDeleteRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/is/deleted/{username}")
    public Boolean isDeleted(@PathVariable String username) {
        return userService.isDeleted(username);
    }
}
