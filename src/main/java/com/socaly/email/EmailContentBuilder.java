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
}
