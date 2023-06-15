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
        Comment comment = commentRepository.findById(commentVoteDto.getCommentId())
                .orElseThrow(() -> new CommentNotFoundException(commentVoteDto.getCommentId().toString()));
        Optional<CommentVote> voteByCommentAndUser = commentVoteRepository.findTopByCommentAndUserOrderByIdDesc(comment, authService.getCurrentUser());

        if (voteByCommentAndUser.isPresent() && voteByCommentAndUser.get().getVoteType().equals(commentVoteDto.getVoteType())) {
            if (VoteType.UPVOTE.equals(commentVoteDto.getVoteType())) {
                comment.setVoteCount(comment.getVoteCount() - 1);
            } else {
                comment.setVoteCount(comment.getVoteCount() + 1);
            }
            commentVoteRepository.deleteById(voteByCommentAndUser.get().getId());

        } else if (voteByCommentAndUser.isPresent()) {
            if (VoteType.UPVOTE.equals(commentVoteDto.getVoteType())) {
                comment.setVoteCount(comment.getVoteCount() + 2);
            } else {
                comment.setVoteCount(comment.getVoteCount() - 2);
            }
            commentVoteRepository.deleteById(voteByCommentAndUser.get().getId());
            commentVoteRepository.save(mapToCommentVote(commentVoteDto, comment));

        } else {
            if (VoteType.UPVOTE.equals(commentVoteDto.getVoteType())) {
                comment.setVoteCount(comment.getVoteCount() + 1);
                User currentUser = authService.getCurrentUser();

                if (!currentUser.getUsername().equals(comment.getUser().getUsername())
                    && comment.getUser().getSettings().getCommentUpVoteEmails()) {
                    sendCommentUpVoteEmail(comment);
                }
            } else {
                comment.setVoteCount(comment.getVoteCount() - 1);
            }
            commentVoteRepository.save(mapToCommentVote(commentVoteDto, comment));
        }
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
                    comment.getPost().getUser().getUsername(),
                    TimeAgo.using(comment.getPost().getCreationDate().toEpochMilli()),
                    comment.getPost().getTitle(),
                    Post.getPostPointsText(comment.getPost().getVoteCount()),
                    Post.getPostCommentCountText(commentRepository.findByPost(comment.getPost()).size()),
                    parentComment.getUser().getUsername(),
                    TimeAgo.using(parentComment.getCreationDate().toEpochMilli()),
                    parentComment.getText(),
                    Comment.getCommentPointsText(parentComment.getVoteCount()),
                    Comment.getCommentReplyCountText(commentRepository.findByParentCommentId(parentComment.getId()).size()),
                    TimeAgo.using(comment.getCreationDate().toEpochMilli()),
                    comment.getText(),
                    Comment.getCommentPointsText(comment.getVoteCount()),
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
                    comment.getPost().getUser().getUsername(),
                    TimeAgo.using(comment.getPost().getCreationDate().toEpochMilli()),
                    comment.getPost().getTitle(),
                    Post.getPostPointsText(comment.getPost().getVoteCount()),
                    Post.getPostCommentCountText(commentRepository.findByPost(comment.getPost()).size()),
                    TimeAgo.using(comment.getCreationDate().toEpochMilli()),
                    comment.getText(),
                    Comment.getCommentPointsText(comment.getVoteCount()),
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