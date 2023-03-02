package com.socaly.comment;

import com.socaly.post.Post;
import com.socaly.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostAndParentCommentIdIsNull(Post post);
    List<Comment> findByPost(Post post);
    List<Comment> findByParentCommentId(Long id);
    List<Comment> findByUser(User user);
}
