package com.socaly.controller;

import com.socaly.dto.UserDto;
import com.socaly.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/getUserDetails/{username}")
    public UserDto getUserDetails(@PathVariable String username) {
        return userService.getUserDetails(username);
    }
}
