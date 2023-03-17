package com.socaly.commentVote;

import com.socaly.comment.Comment;
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
            } else {
                comment.setVoteCount(comment.getVoteCount() - 1);
            }
            commentVoteRepository.save(mapToCommentVote(commentVoteDto, comment));
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