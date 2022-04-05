package com.socaly.service;

import com.socaly.dto.CommentDto;
import com.socaly.dto.CommentResponse;
import com.socaly.entity.Comment;
import com.socaly.entity.NotificationEmail;
import com.socaly.entity.Post;
import com.socaly.entity.User;
import com.socaly.exceptions.CommentNotFoundException;
import com.socaly.exceptions.PostNotFoundException;
import com.socaly.mapper.CommentMapper;
import com.socaly.repository.CommentRepository;
import com.socaly.repository.PostRepository;
import com.socaly.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostID()).orElseThrow(
                () -> new PostNotFoundException(commentDto.getPostID().toString())
        );
        User user = authService.getCurrentUser();
        Comment comment = commentMapper.map(commentDto, post, user);
        commentRepository.save(comment);

        String message = post.getUser().getUsername() + " posted a comment on your post.";
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(
                user.getUsername() + " commented on your post", user.getEmail(), message));
    }

    public CommentResponse getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .stream()
                .map(commentMapper::mapToDto)
                .findFirst()
                .orElseThrow(
                        () -> new CommentNotFoundException(commentId.toString())
                );
    }

    public List<CommentResponse> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException(postId.toString())
        );

        return commentRepository.findByPostAndParentCommentIDIsNull(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getSubCommentsForComment(Long commentId) {
        return commentRepository.findByParentCommentID(commentId)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        return commentRepository.findByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
