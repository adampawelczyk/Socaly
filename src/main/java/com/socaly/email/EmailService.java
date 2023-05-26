package com.socaly.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final EmailContentBuilder emailContentBuilder;

    @Async
    public void sendEmailVerificationEmail(EmailVerificationEmail emailVerificationEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("noreply@socaly.com");
            messageHelper.setTo(emailVerificationEmail.getRecipient());
            messageHelper.setSubject(emailVerificationEmail.getSubject());
            messageHelper.setText(emailContentBuilder.buildEmailVerificationEmail(
                    emailVerificationEmail.getEmailAddress(),
                    emailVerificationEmail.getUsername(),
                    emailVerificationEmail.getVerificationLink(),
                    emailVerificationEmail.getProfileImage()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending email to " + emailVerificationEmail.getRecipient());
        }
    }

    @Async
    public void sendPostCommentEmail(PostCommentEmail postCommentEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("noreply@socaly.com");
            messageHelper.setTo(postCommentEmail.getRecipient());
            messageHelper.setSubject(postCommentEmail.getSubject());
            messageHelper.setText(emailContentBuilder.buildPostCommentEmail(
                    postCommentEmail.getUsername(),
                    postCommentEmail.getCommenterUsername(),
                    postCommentEmail.getCommenterProfileImage(),
                    postCommentEmail.getPostTitle(),
                    postCommentEmail.getPostTimestamp(),
                    postCommentEmail.getCommunityName(),
                    postCommentEmail.getComment()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending email to " + postCommentEmail.getRecipient());
        }
    }
}
