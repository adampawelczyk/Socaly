package com.socaly.comment;

import com.socaly.mail.NotificationEmail;
import com.socaly.post.Post;
import com.socaly.user.User;
import com.socaly.post.PostNotFoundException;
import com.socaly.post.PostRepository;
import com.socaly.user.UserRepository;
import com.socaly.auth.AuthService;
import com.socaly.mail.MailService;
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
    private final MailService mailService;

    public void save(CommentRequest commentRequest) {
        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(
                () -> new PostNotFoundException(commentRequest.getPostId().toString())
        );
        User user = authService.getCurrentUser();
        Comment comment = commentMapper.mapToComment(commentRequest, post, user);
        commentRepository.save(comment);

        String message = post.getUser().getUsername() + " posted a comment on your post.";
        sendCommentNotification(message, post.getUser());
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

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(
                user.getUsername() + " commented on your post", user.getEmail(), message));
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
