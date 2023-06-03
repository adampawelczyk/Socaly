package com.socaly.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailContentBuilder {
    private final TemplateEngine templateEngine;

    String buildEmailVerificationEmail(String recipientEmail, String recipientUsername, String recipientProfileImage,
                                       String verificationLink) {
        Context context = new Context();
        context.setVariable("recipientEmail", recipientEmail);
        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("verificationLink", verificationLink);

        return templateEngine.process("verificationEmailTemplate", context);
    }

    String buildPostCommentEmail(String recipientUsername, String recipientProfileImage, String communityName,
                                 String postTimestamp, String postTitle, String commentUsername,
                                 String commentProfileImage, String commentText) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("communityName", communityName);
        context.setVariable("postTimestamp", postTimestamp);
        context.setVariable("postTitle", postTitle);
        context.setVariable("commentUsername", commentUsername);
        context.setVariable("commentProfileImage", commentProfileImage);
        context.setVariable("commentText", commentText);

        return templateEngine.process("postCommentEmailTemplate", context);
    }

    String buildCommentReplyEmail(String recipientUsername, String recipientProfileImage, String communityName,
                                  String postUsername, String postTimestamp, String postTitle, String commentTimestamp,
                                  String commentText, String replyUsername, String replyUserProfileImage,
                                  String replyText) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("communityName", communityName);
        context.setVariable("postUsername", postUsername);
        context.setVariable("postTimestamp", postTimestamp);
        context.setVariable("postTitle", postTitle);
        context.setVariable("commentTimestamp", commentTimestamp);
        context.setVariable("commentText", commentText);
        context.setVariable("replyUsername", replyUsername);
        context.setVariable("replyUserProfileImage", replyUserProfileImage);
        context.setVariable("replyText", replyText);

        return templateEngine.process("commentReplyEmailTemplate", context);
    }

    String buildPostUpVoteEmail(String recipientUsername, String recipientProfileImage, String communityName,
                                String postTimestamp, String postTitle, String postPoints, String upVoteUsername,
                                String upVotingUserProfileImage) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("communityName", communityName);
        context.setVariable("postTimestamp", postTimestamp);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postPoints", postPoints);
        context.setVariable("upVoteUsername", upVoteUsername);
        context.setVariable("upVotingUserProfileImage", upVotingUserProfileImage);

        return templateEngine.process("postUpVoteEmailTemplate", context);
    }
}
