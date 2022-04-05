package com.socaly.repository;

import com.socaly.entity.Comment;
import com.socaly.entity.Post;
import com.socaly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostAndParentCommentIDIsNull(Post post);
    List<Comment> findByPost(Post post);
    List<Comment> findByParentCommentId(Long id);
    List<Comment> findByUser(User user);
}
