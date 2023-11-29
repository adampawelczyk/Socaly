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
    void savePostVote(final PostVoteDto postVoteDto) {
        final Post post = postRepository.findById(postVoteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(postVoteDto.getPostId().toString()));
        final Optional<PostVote> postVote = postVoteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());

        if (postVote.isPresent() && postVote.get().getVoteType().equals(postVoteDto.getVoteType())) {
            handleSameVoteType(post, postVote.get());
        } else if (postVote.isPresent()) {
            handleDifferentVoteType(post, postVote.get(), postVoteDto);
        } else {
            handleNewVote(post, postVoteDto);
        }
    }

    private void handleSameVoteType(final Post post, final PostVote postVote) {
        if (VoteType.UPVOTE.equals(postVote.getVoteType())) {
            post.setPoints(post.getPoints() - 1);
        } else {
            post.setPoints(post.getPoints() + 1);
        }

        postVoteRepository.deleteById(postVote.getId());
    }

    private void handleDifferentVoteType(final Post post, final PostVote postVote, final PostVoteDto postVoteDto) {
        if (VoteType.UPVOTE.equals(postVoteDto.getVoteType())) {
            post.setPoints(post.getPoints() + 2);
        } else {
            post.setPoints(post.getPoints() - 2);
        }

        postVoteRepository.deleteById(postVote.getId());
        postVoteRepository.save(mapToVote(postVoteDto, post));
    }

    private void handleNewVote(final Post post, final PostVoteDto postVoteDto) {
        if (VoteType.UPVOTE.equals(postVoteDto.getVoteType())) {
            post.setPoints(post.getPoints() + 1);

            if (post.getUser().getSettings().getPostUpVoteEmails()) {
                sendPostUpVoteEmail(post);
            }
        } else {
            post.setPoints(post.getPoints() - 1);
        }

        postVoteRepository.save(mapToVote(postVoteDto, post));
    }

    private void sendPostUpVoteEmail(final Post post) {
        final User currentUser = authService.getCurrentUser();

        emailService.sendPostUpVoteEmail(new PostUpVoteEmail(
                currentUser.getUsername() + " upvoted your post " + post.getTitle() + " in s\\"
                        + post.getCommunity().getName(),
                post.getUser().getEmail(),
                post.getUser().getUsername(),
                post.getUser().getProfileImage().getImageUrl(),
                post.getCommunity().getName(),
                String.valueOf(post.getId()),
                TimeAgo.using(post.getCreationDate().toEpochMilli()),
                post.getTitle(),
                Post.getPostPointsText(post.getPoints()),
                Post.getPostCommentCountText(commentRepository.findByPost(post).size()),
                currentUser.getUsername(),
                currentUser.getProfileImage().getImageUrl()
        ));
    }

    private PostVote mapToVote(final PostVoteDto postVoteDto, final Post post) {
        return PostVote.builder()
                .voteType(postVoteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
