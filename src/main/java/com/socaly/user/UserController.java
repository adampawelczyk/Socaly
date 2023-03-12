package com.socaly.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/getUserDetails/{username}")
    public UserResponse getUserDetails(@PathVariable String username) {
        return userService.getUserDetails(username);
    }
}
