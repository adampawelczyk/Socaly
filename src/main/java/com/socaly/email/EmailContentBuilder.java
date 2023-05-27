package com.socaly.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailContentBuilder {
    private final TemplateEngine templateEngine;

    String buildEmailVerificationEmail(String emailAddress, String username, String verificationLink, String profileImage) {
        Context context = new Context();
        context.setVariable("emailAddress", emailAddress);
        context.setVariable("username", username);
        context.setVariable("verificationLink", verificationLink);
        context.setVariable("profileImage", profileImage);

        return templateEngine.process("verificationEmailTemplate", context);
    }

    String buildPostCommentEmail(String username, String profileImage, String commenterUsername,
                                 String commenterProfileImage, String postTitle, String postTimestamp,
                                 String communityName, String comment) {
        Context context = new Context();

        context.setVariable("username", username);
        context.setVariable("profileImage", profileImage);
        context.setVariable("commenterUsername", commenterUsername);
        context.setVariable("commenterProfileImage", commenterProfileImage);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postTimestamp", postTimestamp);
        context.setVariable("communityName", communityName);
        context.setVariable("comment", comment);

        return templateEngine.process("postCommentEmailTemplate", context);
    }
}
