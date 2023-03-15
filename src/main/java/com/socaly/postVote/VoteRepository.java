package com.socaly.postVote;

import com.socaly.post.Post;
import com.socaly.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<PostVote, Long> {
    Optional<PostVote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
    void deleteById(@NotNull Long voteId);
}
