package com.socaly.postVote;

import com.socaly.auth.AuthService;
import com.socaly.post.Post;
import com.socaly.util.VoteType;
import com.socaly.post.PostNotFoundException;
import com.socaly.post.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(PostVoteDto postVoteDto) {
        Post post = postRepository.findById(postVoteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with id - " + postVoteDto.getPostId()));
        Optional<PostVote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(postVoteDto.getVoteType())) {
            if (VoteType.UPVOTE.equals(postVoteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() - 1);
            } else {
                post.setVoteCount(post.getVoteCount() + 1);
            }

            voteRepository.deleteById(voteByPostAndUser.get().getId());

        } else if (voteByPostAndUser.isPresent()) {
            if (VoteType.UPVOTE.equals(postVoteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 2);
            } else {
                post.setVoteCount(post.getVoteCount() - 2);
            }

            voteRepository.deleteById(voteByPostAndUser.get().getId());
            voteRepository.save(mapToVote(postVoteDto, post));

        } else {
            if (VoteType.UPVOTE.equals(postVoteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 1);
            } else {
                post.setVoteCount(post.getVoteCount() - 1);
            }

            voteRepository.save(mapToVote(postVoteDto, post));
        }
    }

    private PostVote mapToVote(PostVoteDto postVoteDto, Post post) {
        return PostVote.builder()
                .voteType(postVoteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
