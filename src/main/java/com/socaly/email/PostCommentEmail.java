package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentEmail {
    private String subject;
    private String recipient;
    private String username;
    private String commenterUsername;
    private String commenterProfileImage;
    private String communityName;
    private String comment;
}
