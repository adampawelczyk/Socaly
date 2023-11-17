package com.socaly.post;

import com.socaly.community.Community;
import com.socaly.image.Image;
import com.socaly.user.User;
import com.socaly.community.CommunityNotFoundException;
import com.socaly.community.CommunityRepository;
import com.socaly.user.UserRepository;
import com.socaly.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
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

        return postRepository.save(postMapper.mapToPost(postRequest, community, currentUser)).getId();
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(id.toString())
        );

        return postMapper.mapToPostResponse(post);
    }

    public long updatePost(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(id.toString())
        );

        Community community = communityRepository.findByName(postRequest.getCommunityName()).orElseThrow(
                () -> new CommunityNotFoundException(postRequest.getCommunityName())
        );

        User user = authService.getCurrentUser();

        if (Objects.equals(post.getUser().getId(), user.getId())) {
            post.setCommunity(community);
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setEditDate(Instant.now());

            List<Image> images = new ArrayList<>();
            for (String string : postRequest.getImages()) {
                Image image = new Image();
                image.setImageUrl(string);
                images.add(image);
            }

            post.setImages(images);
        }

        return postRepository.save(post).getId();
    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsByCommunity(String communityName) {
        Community community = communityRepository.findByName(communityName).orElseThrow(
                () -> new CommunityNotFoundException(communityName)
        );
        List<Post> posts = postRepository.findAllByCommunity(community);

        return posts
                .stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getAllPostsByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }
}
