package com.socaly.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class SignUpRequest {
    private String username;
    private String password;
    private String email;
}
