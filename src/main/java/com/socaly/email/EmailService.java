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
            throw new EmailException("Exception occurred when sending email verification email to " +
                    emailVerificationEmail.getRecipient());
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
                    postCommentEmail.getProfileImage(),
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
            throw new EmailException("Exception occurred when sending post comment email to " +
                    postCommentEmail.getRecipient());
        }
    }

    @Async
    public void sendCommentReplyEmail(CommentReplyEmail commentReplyEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("noreply@socaly.com");
            messageHelper.setTo(commentReplyEmail.getRecipient());
            messageHelper.setSubject(commentReplyEmail.getSubject());
            messageHelper.setText(emailContentBuilder.buildCommentReplyEmail(
                    commentReplyEmail.getRecipientUsername(),
                    commentReplyEmail.getRecipientProfileImage(),
                    commentReplyEmail.getReplyUsername(),
                    commentReplyEmail.getReplyUserProfileImage(),
                    commentReplyEmail.getPostUsername(),
                    commentReplyEmail.getPostTitle(),
                    commentReplyEmail.getPostTimestamp(),
                    commentReplyEmail.getCommunityName(),
                    commentReplyEmail.getCommentText(),
                    commentReplyEmail.getCommentTimestamp(),
                    commentReplyEmail.getReplyText()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending comment reply email to "
                    + commentReplyEmail.getRecipient());
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
                    postUpVoteEmail.getProfileImage(),
                    postUpVoteEmail.getUpVotingUserUsername(),
                    postUpVoteEmail.getUpVotingUserProfileImage(),
                    postUpVoteEmail.getPostTitle(),
                    postUpVoteEmail.getPostTimestamp(),
                    postUpVoteEmail.getPostPoints(),
                    postUpVoteEmail.getCommunityName()
            ));
        };
        try {
            emailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending post up vote email to "
                    + postUpVoteEmail.getRecipient());
        }
    }
}
