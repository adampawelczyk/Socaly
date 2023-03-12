package com.socaly.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse getUserDetails(String username) {
        return userRepository.findByUsername(username)
                .stream().map(userMapper::mapToDto)
                .findFirst()
                .orElseThrow(
                    () -> new UsernameNotFoundException(username)
                );
    }
}
