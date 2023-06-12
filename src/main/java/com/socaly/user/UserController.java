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
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/get/email")
    public String getCurrentUserEmail() {
        return userService.getCurrentUserEmail();
    }

    @GetMapping("/is-email-verified")
    public Boolean isEmailVerified() {
        return userService.isEmailVerified();
    }

    @GetMapping("/get/{username}")
    public UserResponse getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/get/profile/image/{username}")
    public String getUserProfileImage(@PathVariable String username) {
        return userService.getUserProfileImage(username);
    }

    @PatchMapping("/change/profile/image")
    public ResponseEntity<Void> changeProfileImage(@RequestBody String imageUrl) {
        userService.updateProfileImage(imageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/profile/banner")
    public ResponseEntity<Void> changeProfileBanner(@RequestBody String imageUrl) {
        userService.updateProfileBanner(imageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change/description")
    public ResponseEntity<Void> changeDescription(@RequestBody(required = false) String description) {
        userService.updateDescription(description);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/email")
    public ResponseEntity<Void> updateEmail(@RequestBody EmailUpdateRequest emailUpdateRequest) {
        userService.updateEmail(emailUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/password")
    public ResponseEntity<Void> updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        userService.updatePassword(passwordUpdateRequest);
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
