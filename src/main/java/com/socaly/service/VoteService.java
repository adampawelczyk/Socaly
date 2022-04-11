package com.socaly.service;

import com.socaly.dto.VoteDto;
import com.socaly.entity.Post;
import com.socaly.entity.Vote;
import com.socaly.entity.VoteType;
import com.socaly.exceptions.PostNotFoundException;
import com.socaly.repository.PostRepository;
import com.socaly.repository.VoteRepository;
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
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with id - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() - 1);
            } else {
                post.setVoteCount(post.getVoteCount() + 1);
            }

            voteRepository.deleteById(voteByPostAndUser.get().getId());

        } else if (voteByPostAndUser.isPresent()) {
            if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 2);
            } else {
                post.setVoteCount(post.getVoteCount() - 2);
            }

            voteRepository.deleteById(voteByPostAndUser.get().getId());
            voteRepository.save(mapToVote(voteDto, post));

        } else {
            if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 1);
            } else {
                post.setVoteCount(post.getVoteCount() - 1);
            }

            voteRepository.save(mapToVote(voteDto, post));
        }
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
