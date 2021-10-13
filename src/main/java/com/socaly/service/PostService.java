package com.socaly.service;

import com.socaly.dto.PostRequest;
import com.socaly.dto.PostResponse;
import com.socaly.entity.Community;
import com.socaly.entity.Post;
import com.socaly.entity.User;
import com.socaly.exceptions.CommunityNotFoundException;
import com.socaly.exceptions.PostNotFoundException;
import com.socaly.mapper.PostMapper;
import com.socaly.repository.CommunityRepository;
import com.socaly.repository.PostRepository;
import com.socaly.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final CommunityRepository communityRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Community community = communityRepository.findByName(postRequest.getCommunityName()).orElseThrow(
                () -> new CommunityNotFoundException(postRequest.getCommunityName())
        );
        User currentUser = authService.getCurrentUser();

        postRepository.save(postMapper.map(postRequest, community, currentUser));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(id.toString())
        );

        return postMapper.mapToDto(post);
    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCommunity(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(
                () -> new CommunityNotFoundException(communityId.toString())
        );
        List<Post> posts = postRepository.findAllByCommunity(community);

        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
