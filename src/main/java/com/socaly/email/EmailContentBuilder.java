package com.socaly.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailContentBuilder {
    private final TemplateEngine templateEngine;

    String buildEmailVerificationEmail(String emailAddress, String username, String verificationLink) {
        Context context = new Context();
        context.setVariable("emailAddress", emailAddress);
        context.setVariable("username", username);
        context.setVariable("verificationLink", verificationLink);

        return templateEngine.process("verificationEmailTemplate", context);
    }
}
