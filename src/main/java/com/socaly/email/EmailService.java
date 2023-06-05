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
            messageHelper.setTo(emailVerificationEmail.getRecipientEmail());
            messageHelper.setSubject(emailVerificationEmail.getSubject());
            messageHelper.setText(emailContentBuilder.buildEmailVerificationEmail(
                    emailVerificationEmail.getRecipientEmail(),
                    emailVerificationEmail.getRecipientUsername(),
                    emailVerificationEmail.getRecipientProfileImage(),
                    emailVerificationEmail.getVerificationLink()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending email verification email to " +
                    emailVerificationEmail.getRecipientEmail());
        }
    }

    @Async
    public void sendPostCommentEmail(PostCommentEmail postCommentEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("noreply@socaly.com");
            messageHelper.setTo(postCommentEmail.getRecipientEmail());
            messageHelper.setSubject(postCommentEmail.getSubject());
            messageHelper.setText(emailContentBuilder.buildPostCommentEmail(
                    postCommentEmail.getRecipientUsername(),
                    postCommentEmail.getRecipientProfileImage(),
                    postCommentEmail.getCommunityName(),
                    postCommentEmail.getPostTimestamp(),
                    postCommentEmail.getPostTitle(),
                    postCommentEmail.getCommentUsername(),
                    postCommentEmail.getCommentUserProfileImage(),
                    postCommentEmail.getCommentText()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending post comment email to " +
                    postCommentEmail.getRecipientEmail());
        }
    }

    @Async
    public void sendCommentReplyEmail(CommentReplyEmail commentReplyEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("noreply@socaly.com");
            messageHelper.setTo(commentReplyEmail.getRecipientEmail());
            messageHelper.setSubject(commentReplyEmail.getSubject());
            messageHelper.setText(emailContentBuilder.buildCommentReplyEmail(
                    commentReplyEmail.getRecipientUsername(),
                    commentReplyEmail.getRecipientProfileImage(),
                    commentReplyEmail.getCommunityName(),
                    commentReplyEmail.getPostUsername(),
                    commentReplyEmail.getPostTimestamp(),
                    commentReplyEmail.getPostTitle(),
                    commentReplyEmail.getCommentTimestamp(),
                    commentReplyEmail.getCommentText(),
                    commentReplyEmail.getReplyUsername(),
                    commentReplyEmail.getReplyUserProfileImage(),
                    commentReplyEmail.getReplyText()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending comment reply email to "
                    + commentReplyEmail.getRecipientEmail());
        }
    }

    @Async
    public void sendPostUpVoteEmail(PostUpVoteEmail postUpVoteEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("noreply@socaly.com");
            messageHelper.setTo(postUpVoteEmail.getRecipient());
            messageHelper.setSubject(postUpVoteEmail.getSubject());
            messageHelper.setText(emailContentBuilder.buildPostUpVoteEmail(
                    postUpVoteEmail.getRecipientUsername(),
                    postUpVoteEmail.getRecipientProfileImage(),
                    postUpVoteEmail.getCommunityName(),
                    postUpVoteEmail.getPostTimestamp(),
                    postUpVoteEmail.getPostTitle(),
                    postUpVoteEmail.getPostPoints(),
                    postUpVoteEmail.getCommentCount(),
                    postUpVoteEmail.getUpVoteUsername(),
                    postUpVoteEmail.getUpVoteUserProfileImage()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending post up vote email to "
                    + postUpVoteEmail.getRecipient());
        }
    }

    @Async
    public void sendCommentUpVoteEmail(CommentUpVoteEmail commentUpVoteEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("noreply@socaly.com");
            messageHelper.setTo(commentUpVoteEmail.getRecipient());
            messageHelper.setSubject(commentUpVoteEmail.getSubject());
            messageHelper.setText(emailContentBuilder.buildCommentUpVoteEmail(
                    commentUpVoteEmail.getRecipientUsername(),
                    commentUpVoteEmail.getRecipientProfileImage(),
                    commentUpVoteEmail.getCommunityName(),
                    commentUpVoteEmail.getPostUsername(),
                    commentUpVoteEmail.getPostTimestamp(),
                    commentUpVoteEmail.getPostTitle(),
                    commentUpVoteEmail.getPostPoints(),
                    commentUpVoteEmail.getCommentCount(),
                    commentUpVoteEmail.getCommentTimestamp(),
                    commentUpVoteEmail.getCommentText(),
                    commentUpVoteEmail.getCommentPoints(),
                    commentUpVoteEmail.getCommentReplyCount(),
                    commentUpVoteEmail.getUpVoteUsername(),
                    commentUpVoteEmail.getUpVoteUserProfileImage()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending comment up vote email to "
                    + commentUpVoteEmail.getRecipient());
        }
    }
}
