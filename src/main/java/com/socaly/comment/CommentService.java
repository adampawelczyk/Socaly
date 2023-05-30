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
        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(
                () -> new PostNotFoundException(commentRequest.getPostId().toString())
        );
        User user = authService.getCurrentUser();
        Comment comment = commentMapper.mapToComment(commentRequest, post, user);
        commentRepository.save(comment);

        if (!post.getUser().getUsername().equals(comment.getUser().getUsername()) && comment.getParentCommentId() == null
            && post.getUser().getSettings().getPostCommentEmails()) {
            sendPostCommentEmail(post, comment);
        } else if (comment.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(comment.getParentCommentId()).orElseThrow(
                    () -> new CommentNotFoundException(comment.getParentCommentId().toString())
            );

            if (!comment.getUser().getUsername().equals(parentComment.getUser().getUsername())
                    && parentComment.getUser().getSettings().getCommentReplyEmails()) {
                sendCommentReplyEmail(post, parentComment, comment);
            }
        }
    }

    private void sendPostCommentEmail(Post post, Comment comment) {
        emailService.sendPostCommentEmail(new PostCommentEmail(
                comment.getUser().getUsername() + " commented on your post: " + post.getPostName(),
                post.getUser().getEmail(),
                post.getUser().getUsername(),
                post.getUser().getProfileImage().getImageUrl(),
                comment.getUser().getUsername(),
                comment.getUser().getProfileImage().getImageUrl(),
                post.getPostName(),
                TimeAgo.using(post.getCreatedDate().toEpochMilli()),
                post.getCommunity().getName(),
                comment.getText()
        ));
    }

    private void sendCommentReplyEmail(Post post, Comment comment, Comment reply) {
        emailService.sendCommentReplyEmail(new CommentReplyEmail(
                reply.getUser().getUsername() + " replied to your comment on post: " + post.getPostName(),
                comment.getUser().getEmail(),
                comment.getUser().getUsername(),
                comment.getUser().getProfileImage().getImageUrl(),
                reply.getUser().getUsername(),
                reply.getUser().getProfileImage().getImageUrl(),
                post.getUser().getUsername(),
                post.getPostName(),
                TimeAgo.using(post.getCreatedDate().toEpochMilli()),
                post.getCommunity().getName(),
                comment.getText(),
                TimeAgo.using(comment.getCreationDate().toEpochMilli()),
                reply.getText()
        ));
    }

    public void edit(Long commentId, String text) {
        Comment commentToEdit = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException(commentId.toString())
        );
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
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException(postId.toString())
        );

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
