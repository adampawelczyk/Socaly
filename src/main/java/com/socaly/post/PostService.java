package com.socaly.post;

import com.socaly.community.Community;
import com.socaly.user.User;
import com.socaly.community.CommunityNotFoundException;
import com.socaly.community.CommunityRepository;
import com.socaly.user.UserRepository;
import com.socaly.auth.AuthService;
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

    public long save(PostRequest postRequest) {
        Community community = communityRepository.findByName(postRequest.getCommunityName()).orElseThrow(
                () -> new CommunityNotFoundException(postRequest.getCommunityName())
        );
        User currentUser = authService.getCurrentUser();

        return postRepository.save(postMapper.map(postRequest, community, currentUser)).getId();
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
    public List<PostResponse> getPostsByCommunity(String communityName) {
        Community community = communityRepository.findByName(communityName).orElseThrow(
                () -> new CommunityNotFoundException(communityName)
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
