package com.socaly.postVote;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.socaly.auth.AuthService;
import com.socaly.comment.CommentRepository;
import com.socaly.email.EmailService;
import com.socaly.email.PostUpVoteEmail;
import com.socaly.post.Post;
import com.socaly.user.User;
import com.socaly.util.VoteType;
import com.socaly.post.PostNotFoundException;
import com.socaly.post.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostVoteService {
    private final PostVoteRepository postVoteRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final EmailService emailService;

    @Transactional
    public void vote(PostVoteDto postVoteDto) {
        Post post = postRepository.findById(postVoteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(postVoteDto.getPostId().toString()));
        Optional<PostVote> voteByPostAndUser = postVoteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(postVoteDto.getVoteType())) {
            if (VoteType.UPVOTE.equals(postVoteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() - 1);
            } else {
                post.setVoteCount(post.getVoteCount() + 1);
            }

            postVoteRepository.deleteById(voteByPostAndUser.get().getId());

        } else if (voteByPostAndUser.isPresent()) {
            if (VoteType.UPVOTE.equals(postVoteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 2);
            } else {
                post.setVoteCount(post.getVoteCount() - 2);
            }

            postVoteRepository.deleteById(voteByPostAndUser.get().getId());
            postVoteRepository.save(mapToVote(postVoteDto, post));

        } else {
            if (VoteType.UPVOTE.equals(postVoteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 1);

                if (post.getUser().getSettings().getPostUpVoteEmails()) {
                    sendPostUpVoteEmail(post);
                }
            } else {
                post.setVoteCount(post.getVoteCount() - 1);
            }

            postVoteRepository.save(mapToVote(postVoteDto, post));
        }
    }

    private void sendPostUpVoteEmail(Post post) {
        User currentUser = authService.getCurrentUser();
        String postPoints;
        int commentCount = commentRepository.findByPost(post).size();
        String commentCountText;

        if (post.getVoteCount() == 1) {
            postPoints = "1 point";
        } else {
            postPoints = post.getVoteCount() + " points";
        }

        if (commentCount == 1) {
            commentCountText = "1 comment";
        } else {
            commentCountText = commentCount + " comments";
        }

        emailService.sendPostUpVoteEmail(new PostUpVoteEmail(
                post.getUser().getUsername() + " upvoted your post " + post.getPostName(),
                post.getUser().getEmail(),
                post.getUser().getUsername(),
                post.getUser().getProfileImage().getImageUrl(),
                post.getCommunity().getName(),
                TimeAgo.using(post.getCreatedDate().toEpochMilli()),
                post.getPostName(),
                postPoints,
                commentCountText,
                currentUser.getUsername(),
                currentUser.getProfileImage().getImageUrl()
        ));
    }

    private PostVote mapToVote(PostVoteDto postVoteDto, Post post) {
        return PostVote.builder()
                .voteType(postVoteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
