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

    String buildCommentReplyEmail(String recipientUsername, String profileImage, String replyingUsername, String replyingUserProfileImage,
                                  String postingUsername, String postTitle, String postTimestamp, String communityName,
                                  String commentText, String commentTimestamp, String replyText) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("profileImage", profileImage);
        context.setVariable("replyingUsername", replyingUsername);
        context.setVariable("replyingUserProfileImage", replyingUserProfileImage);
        context.setVariable("postingUsername", postingUsername);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postTimestamp", postTimestamp);
        context.setVariable("communityName", communityName);
        context.setVariable("commentText", commentText);
        context.setVariable("commentTimestamp", commentTimestamp);
        context.setVariable("replyText", replyText);

        return templateEngine.process("commentReplyEmailTemplate", context);
    }

    String buildPostUpVoteEmail(String recipientUsername, String profileImage, String upVotingUserUsername, String upVotingUserProfileImage,
                                String postTitle, String postTimestamp, String postPoints, String communityName) {
        Context context = new Context();

        context.setVariable("username", recipientUsername);
        context.setVariable("profileImage", profileImage);
        context.setVariable("upVotingUserUsername", upVotingUserUsername);
        context.setVariable("upVotingUserProfileImage", upVotingUserProfileImage);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postTimestamp", postTimestamp);
        context.setVariable("postPoints", postPoints);
        context.setVariable("communityName", communityName);

        return templateEngine.process("postUpVoteEmailTemplate", context);
    }
}
