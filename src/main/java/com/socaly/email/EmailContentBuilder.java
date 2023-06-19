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
                                 String postTimeSinceCreation, String postTitle, String postPoints, String commentCount,
                                 String commentUsername, String commentProfileImage, String commentText) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("communityName", communityName);
        context.setVariable("postTimeSinceCreation", postTimeSinceCreation);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postPoints", postPoints);
        context.setVariable("commentCount", commentCount);
        context.setVariable("commentUsername", commentUsername);
        context.setVariable("commentProfileImage", commentProfileImage);
        context.setVariable("commentText", commentText);

        return templateEngine.process("postCommentEmailTemplate", context);
    }

    String buildCommentReplyEmail(String recipientUsername, String recipientProfileImage, String communityName,
                                  String postUsername, String postTimeSinceCreation, String postId, String postTitle,
                                  String postPoints, String commentCount, String commentTimeSinceCreation,
                                  String commentText, String commentPoints, String commentReplyCount, String replyId,
                                  String replyUsername, String replyUserProfileImage, String replyText) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("communityName", communityName);
        context.setVariable("postUsername", postUsername);
        context.setVariable("postTimeSinceCreation", postTimeSinceCreation);
        context.setVariable("postId", postId);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postPoints", postPoints);
        context.setVariable("commentCount", commentCount);
        context.setVariable("commentTimeSinceCreation", commentTimeSinceCreation);
        context.setVariable("commentText", commentText);
        context.setVariable("commentPoints", commentPoints);
        context.setVariable("commentReplyCount", commentReplyCount);
        context.setVariable("replyId", replyId);
        context.setVariable("replyUsername", replyUsername);
        context.setVariable("replyUserProfileImage", replyUserProfileImage);
        context.setVariable("replyText", replyText);

        return templateEngine.process("commentReplyEmailTemplate", context);
    }

    String buildPostUpVoteEmail(String recipientUsername, String recipientProfileImage, String communityName,
                                String postTimeSinceCreation, String postTitle, String postPoints, String commentCount,
                                String upVoteUsername, String upVoteUserProfileImage) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("communityName", communityName);
        context.setVariable("postTimeSinceCreation", postTimeSinceCreation);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postPoints", postPoints);
        context.setVariable("commentCount", commentCount);
        context.setVariable("upVoteUsername", upVoteUsername);
        context.setVariable("upVoteUserProfileImage", upVoteUserProfileImage);

        return templateEngine.process("postUpVoteEmailTemplate", context);
    }

    String buildCommentUpVoteEmail(String recipientUsername, String recipientProfileImage, String communityName,
                                   String postUsername, String postTimeSinceCreation, String postTitle,
                                   String postPoints, String commentCount, String commentTimeSinceCreation,
                                   String commentText, String commentPoints, String commentReplyCount,
                                   String upVoteUsername, String upVoteUserProfileImage) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("communityName", communityName);
        context.setVariable("postUsername", postUsername);
        context.setVariable("postTimeSinceCreation", postTimeSinceCreation);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postPoints", postPoints);
        context.setVariable("commentCount", commentCount);
        context.setVariable("commentTimeSinceCreation", commentTimeSinceCreation);
        context.setVariable("commentText", commentText);
        context.setVariable("commentPoints", commentPoints);
        context.setVariable("commentReplyCount", commentReplyCount);
        context.setVariable("upVoteUsername", upVoteUsername);
        context.setVariable("upVoteUserProfileImage", upVoteUserProfileImage);

        return templateEngine.process("commentUpVoteEmailTemplate", context);
    }

    String buildReplyUpVoteEmail(String recipientUsername, String recipientProfileImage, String communityName,
                                 String postUsername, String postTimeSinceCreation, String postTitle, String postPoints,
                                 String commentCount, String commentUsername, String commentTimeSinceCreation,
                                 String commentText, String commentPoints, String commentReplyCount,
                                 String replyTimeSinceCreation, String replyText, String replyPoints,
                                 String replyReplyCount,  String upVoteUsername, String upVoteUserProfileImage) {
        Context context = new Context();

        context.setVariable("recipientUsername", recipientUsername);
        context.setVariable("recipientProfileImage", recipientProfileImage);
        context.setVariable("communityName", communityName);
        context.setVariable("postUsername", postUsername);
        context.setVariable("postTimeSinceCreation", postTimeSinceCreation);
        context.setVariable("postTitle", postTitle);
        context.setVariable("postPoints", postPoints);
        context.setVariable("commentCount", commentCount);
        context.setVariable("commentUsername", commentUsername);
        context.setVariable("commentTimeSinceCreation", commentTimeSinceCreation);
        context.setVariable("commentText", commentText);
        context.setVariable("commentPoints", commentPoints);
        context.setVariable("commentReplyCount", commentReplyCount);
        context.setVariable("replyTimeSinceCreation", replyTimeSinceCreation);
        context.setVariable("replyText", replyText);
        context.setVariable("replyPoints", replyPoints);
        context.setVariable("replyReplyCount", replyReplyCount);
        context.setVariable("upVoteUsername", upVoteUsername);
        context.setVariable("upVoteUserProfileImage", upVoteUserProfileImage);

        return templateEngine.process("replyUpVoteEmailTemplate", context);
    }
}
