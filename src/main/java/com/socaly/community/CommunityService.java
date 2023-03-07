package com.socaly.community;

import com.socaly.user.User;
import com.socaly.exceptions.SocalyException;
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
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional
    public CommunityDto save(CommunityRequest communityRequest) {
        User currentUser = authService.getCurrentUser();
        Community save = communityRepository.save(communityMapper.mapDtoToCommunity(communityRequest, currentUser));
        join(communityRequest.getName());

        return communityMapper.mapCommunityToDto(save);
    }

    @Transactional(readOnly = true)
    public List<CommunityDto> getAll() {
        return communityRepository.findAll().stream().map(communityMapper::mapCommunityToDto).collect(Collectors.toList());
    }

    public CommunityDto getCommunity(String name) {
        Community community = communityRepository.findByName(name).orElseThrow(
                () -> new SocalyException("No community found with name - " + name));

        return communityMapper.mapCommunityToDto(community);
    }

    public void join(String name) {
        User currentUser = authService.getCurrentUser();
        Community community = communityRepository.findByName(name).orElseThrow(
                () -> new SocalyException("No community found with name - " + name)
        );

        community.getUsers().add(currentUser);
        communityRepository.save(community);
    }

    public void leave(String name) {
        User currentUser = authService.getCurrentUser();
        Community community = communityRepository.findByName(name).orElseThrow(
                () -> new SocalyException("No community found with name - " + name)
        );

        community.getUsers().remove(currentUser);
        communityRepository.save(community);
    }

    public List<CommunityDto> getAllCommunitiesForUser(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("No user found with name - " + name)
        );

        return communityRepository
                .findAll()
                .stream()
                .filter(community -> community.getUsers().contains(user))
                .map(communityMapper::mapCommunityToDto)
                .collect(Collectors.toList());
    }
}
