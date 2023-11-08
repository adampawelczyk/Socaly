package com.socaly.comment;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.socaly.email.CommentReplyEmail;
import com.socaly.email.PostCommentEmail;
import com.socaly.post.Post;
import com.socaly.user.User;
import com.socaly.post.PostNotFoundException;
import com.socaly.post.PostRepository;
import com.socaly.user.UserRepository;
import com.socaly.auth.AuthService;
import com.socaly.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final EmailService emailService;

    public void save(CommentRequest commentRequest) {
        final Post post = findPostById(commentRequest.getPostId());
        User user = authService.getCurrentUser();
        Comment comment = commentMapper.mapToComment(commentRequest, post, user);
        commentRepository.save(comment);

        if (shouldSendPostCommentEmail(post, comment)) {
            sendPostCommentEmail(post, comment);
        } else if (comment.getParentCommentId() != null) {
            Comment parentComment = findCommentById(comment.getParentCommentId());

            if (shouldSendCommentReplyEmail(comment, parentComment)) {
                sendCommentReplyEmail(post, parentComment, comment);
            }
        }
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException(postId.toString())
        );
    }

    private boolean shouldSendPostCommentEmail(Post post, Comment comment) {
        return !post.getUser().getUsername().equals(comment.getUser().getUsername()) &&
                comment.getParentCommentId() == null &&
                post.getUser().getSettings().getPostCommentEmails();
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                    () -> new CommentNotFoundException(commentId.toString())
            );
    }

    private boolean shouldSendCommentReplyEmail(Comment comment, Comment parentComment) {
        return !comment.getUser().getUsername().equals(parentComment.getUser().getUsername()) &&
                parentComment.getUser().getSettings().getCommentReplyEmails();
    }

    private void sendPostCommentEmail(Post post, Comment comment) {
        emailService.sendPostCommentEmail(new PostCommentEmail(
                comment.getUser().getUsername() + " commented on your post " + post.getTitle() + " in s\\"
                        + post.getCommunity().getName(),
                post.getUser().getEmail(),
                post.getUser().getUsername(),
                post.getUser().getProfileImage().getImageUrl(),
                post.getCommunity().getName(),
                String.valueOf(post.getId()),
                TimeAgo.using(post.getCreationDate().toEpochMilli()),
                post.getTitle(),
                Post.getPostPointsText(post.getPoints()),
                Post.getPostCommentCountText(commentRepository.findByPost(comment.getPost()).size()),
                String.valueOf(comment.getId()),
                comment.getUser().getUsername(),
                comment.getUser().getProfileImage().getImageUrl(),
                comment.getText()
        ));
    }

    private void sendCommentReplyEmail(Post post, Comment comment, Comment reply) {
        emailService.sendCommentReplyEmail(new CommentReplyEmail(
                reply.getUser().getUsername() + " replied to your comment on post " + post.getTitle()
                        + "in s\\" + post.getCommunity().getName(),
                comment.getUser().getEmail(),
                comment.getUser().getUsername(),
                comment.getUser().getProfileImage().getImageUrl(),
                post.getCommunity().getName(),
                String.valueOf(post.getId()),
                post.getUser().getUsername(),
                TimeAgo.using(post.getCreationDate().toEpochMilli()),
                post.getTitle(),
                Post.getPostPointsText(comment.getPost().getPoints()),
                Post.getPostCommentCountText(commentRepository.findByPost(comment.getPost()).size()),
                TimeAgo.using(comment.getCreationDate().toEpochMilli()),
                comment.getText(),
                Comment.getCommentPointsText(comment.getPoints()),
                Comment.getCommentReplyCountText(commentRepository.findByParentCommentId(comment.getId()).size()),
                String.valueOf(reply.getId()),
                reply.getUser().getUsername(),
                reply.getUser().getProfileImage().getImageUrl(),
                reply.getText()
        ));
    }

    public void edit(Long commentId, String text) {
        Comment commentToEdit = findCommentById(commentId);
        User user = authService.getCurrentUser();

        if (Objects.equals(commentToEdit.getUser().getId(), user.getId())) {
            commentToEdit.setText(text);
            commentToEdit.setEditDate(Instant.now());
            commentRepository.save(commentToEdit);
        }
    }

    public CommentResponse getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .stream()
                .map(commentMapper::mapToCommentResponse)
                .findFirst()
                .orElseThrow(
                        () -> new CommentNotFoundException(commentId.toString())
                );
    }

    public List<CommentResponse> getAllCommentsForPost(Long postId) {
        Post post = findPostById(postId);

        return commentRepository.findByPostAndParentCommentIdIsNull(post)
                .stream()
                .map(commentMapper::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getSubCommentsForComment(Long commentId) {
        return commentRepository.findByParentCommentId(commentId)
                .stream()
                .map(commentMapper::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        return commentRepository.findByUser(user)
                .stream()
                .map(commentMapper::mapToCommentResponse)
                .collect(Collectors.toList());
    }
}
