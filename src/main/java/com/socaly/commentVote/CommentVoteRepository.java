package com.socaly.commentVote;

import com.socaly.comment.Comment;
import com.socaly.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    Optional<CommentVote> findTopByCommentAndUserOrderByIdDesc(Comment comment, User currentUser);
    void deleteById(@NotNull Long commentId);
}
