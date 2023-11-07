package com.socaly.commentVote;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.socaly.comment.Comment;
import com.socaly.email.CommentUpVoteEmail;
import com.socaly.email.EmailService;
import com.socaly.email.ReplyUpVoteEmail;
import com.socaly.post.Post;
import com.socaly.user.User;
import com.socaly.util.VoteType;
import com.socaly.comment.CommentNotFoundException;
import com.socaly.comment.CommentRepository;
import com.socaly.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentVoteService {
    private final CommentVoteRepository commentVoteRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final EmailService emailService;

    @Transactional
    public void vote(CommentVoteDto commentVoteDto) {
        Comment comment = findCommentById(commentVoteDto.getCommentId());
        Optional<CommentVote> voteByCommentAndUser = commentVoteRepository.findTopByCommentAndUserOrderByIdDesc(comment, authService.getCurrentUser());

        if (voteByCommentAndUser.isPresent()) {
            handleExistingVote(comment, commentVoteDto, voteByCommentAndUser.get());
        } else {
            handleNewVote(comment, commentVoteDto);
        }
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(
                () -> new CommentNotFoundException(commentId.toString())
            );
    }

    private void handleExistingVote(final Comment comment, final CommentVoteDto commentVoteDto, final CommentVote existingVote) {
        if (existingVote.getVoteType() == commentVoteDto.getVoteType()) {
            comment.setPoints(comment.getPoints() + (commentVoteDto.getVoteType() == VoteType.UPVOTE ? -1 : 1));
            commentVoteRepository.deleteById(existingVote.getId());
        } else {
            int pointsChange = (commentVoteDto.getVoteType() == VoteType.UPVOTE ? 2 : -2);
            comment.setPoints(comment.getPoints() + pointsChange);
            commentVoteRepository.deleteById(existingVote.getId());
            commentVoteRepository.save(mapToCommentVote(commentVoteDto, comment));
        }
    }

    private void handleNewVote(Comment comment, CommentVoteDto commentVoteDto) {
        final User currentUser = authService.getCurrentUser();
        final int pointsChange = (commentVoteDto.getVoteType() == VoteType.UPVOTE ? 1 : -1);
        comment.setPoints(comment.getPoints() + pointsChange);

        if (commentVoteDto.getVoteType() == VoteType.UPVOTE
                && !currentUser.getUsername().equals(comment.getUser().getUsername())
                && comment.getUser().getSettings().getCommentUpVoteEmails()) {
            sendCommentUpVoteEmail(comment);
        }

        commentVoteRepository.save(mapToCommentVote(commentVoteDto, comment));
    }

    private void sendCommentUpVoteEmail(Comment comment) {
        User currentUser = authService.getCurrentUser();

        if (comment.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(comment.getParentCommentId())
                    .stream()
                    .findFirst()
                    .orElseThrow(
                            () -> new CommentNotFoundException(comment.getParentCommentId().toString())
                    );

            emailService.sendReplyUpVoteEmail(new ReplyUpVoteEmail(
                    currentUser.getUsername() + " upvoted your reply on post " + comment.getPost().getTitle()
                            + " in s\\" + comment.getPost().getCommunity().getName(),
                    comment.getUser().getEmail(),
                    comment.getUser().getUsername(),
                    comment.getUser().getProfileImage().getImageUrl(),
                    comment.getPost().getCommunity().getName(),
                    String.valueOf(comment.getPost().getId()),
                    comment.getPost().getUser().getUsername(),
                    TimeAgo.using(comment.getPost().getCreationDate().toEpochMilli()),
                    comment.getPost().getTitle(),
                    Post.getPostPointsText(comment.getPost().getPoints()),
                    Post.getPostCommentCountText(commentRepository.findByPost(comment.getPost()).size()),
                    parentComment.getUser().getUsername(),
                    TimeAgo.using(parentComment.getCreationDate().toEpochMilli()),
                    parentComment.getText(),
                    Comment.getCommentPointsText(parentComment.getPoints()),
                    Comment.getCommentReplyCountText(commentRepository.findByParentCommentId(parentComment.getId()).size()),
                    String.valueOf(comment.getId()),
                    TimeAgo.using(comment.getCreationDate().toEpochMilli()),
                    comment.getText(),
                    Comment.getCommentPointsText(comment.getPoints()),
                    Comment.getCommentReplyCountText(commentRepository.findByParentCommentId(comment.getId()).size()),
                    currentUser.getUsername(),
                    currentUser.getProfileImage().getImageUrl()
            ));
        } else {
            emailService.sendCommentUpVoteEmail(new CommentUpVoteEmail(
                    currentUser.getUsername() + " upvoted your comment on post " + comment.getPost().getTitle()
                            + " in s\\" + comment.getPost().getCommunity().getName(),
                    comment.getUser().getEmail(),
                    comment.getUser().getUsername(),
                    comment.getUser().getProfileImage().getImageUrl(),
                    comment.getPost().getCommunity().getName(),
                    String.valueOf(comment.getPost().getId()),
                    comment.getPost().getUser().getUsername(),
                    TimeAgo.using(comment.getPost().getCreationDate().toEpochMilli()),
                    comment.getPost().getTitle(),
                    Post.getPostPointsText(comment.getPost().getPoints()),
                    Post.getPostCommentCountText(commentRepository.findByPost(comment.getPost()).size()),
                    String.valueOf(comment.getId()),
                    TimeAgo.using(comment.getCreationDate().toEpochMilli()),
                    comment.getText(),
                    Comment.getCommentPointsText(comment.getPoints()),
                    Comment.getCommentReplyCountText(commentRepository.findByParentCommentId(comment.getId()).size()),
                    currentUser.getUsername(),
                    currentUser.getProfileImage().getImageUrl()
            ));
        }
    }

    private CommentVote mapToCommentVote(CommentVoteDto commentVoteDto, Comment comment) {
        return CommentVote.builder()
                .voteType(commentVoteDto.getVoteType())
                .comment(comment)
                .user(authService.getCurrentUser())
                .build();
    }
}