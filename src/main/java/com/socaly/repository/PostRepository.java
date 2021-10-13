package com.socaly.repository;

import com.socaly.entity.Community;
import com.socaly.entity.Post;
import com.socaly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCommunity(Community community);
    List<Post> findByUser(User user);
}
