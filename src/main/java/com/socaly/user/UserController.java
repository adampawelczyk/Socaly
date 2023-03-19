package com.socaly.user;

import lombok.AllArgsConstructor;
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
}
