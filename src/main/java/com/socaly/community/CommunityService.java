package com.socaly.community;

import com.socaly.user.User;
import com.socaly.user.UserRepository;
import com.socaly.auth.AuthService;
import com.socaly.userCommunitySettings.UserCommunitySettings;
import com.socaly.userCommunitySettings.UserCommunitySettingsNotFoundException;
import com.socaly.userCommunitySettings.UserCommunitySettingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserCommunitySettingsRepository userCommunitySettingsRepository;
    private final CommunityMapper communityMapper;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional
    public CommunityResponse save(CommunityRequest communityRequest) {
        User currentUser = authService.getCurrentUser();
        Community save = communityRepository.save(communityMapper.mapToCommunity(communityRequest, currentUser));
        join(communityRequest.getName());

        return communityMapper.mapToCommunityResponse(save);
    }

    @Transactional(readOnly = true)
    public List<CommunityResponse> getAll() {
        return communityRepository
                .findAll()
                .stream()
                .map(communityMapper::mapToCommunityResponse)
                .collect(Collectors.toList());
    }

    public CommunityResponse getCommunity(String name) {
        Community community = communityRepository.findByName(name).orElseThrow(
                () -> new CommunityNotFoundException(name));

        return communityMapper.mapToCommunityResponse(community);
    }

    public void join(String name) {
        User currentUser = authService.getCurrentUser();
        Community community = communityRepository.findByName(name).orElseThrow(
                () -> new CommunityNotFoundException(name)
        );

        community.getUsers().add(currentUser);
        communityRepository.save(community);

        UserCommunitySettings userCommunitySettings = new UserCommunitySettings();
        userCommunitySettings.setCommunityId(community.getId());
        userCommunitySettingsRepository.save(userCommunitySettings);

        currentUser.getUserCommunitySettings().add(userCommunitySettings);
        userRepository.save(currentUser);
    }

    public void leave(String name) {
        User currentUser = authService.getCurrentUser();
        Community community = communityRepository.findByName(name).orElseThrow(
                () -> new CommunityNotFoundException(name)
        );

        community.getUsers().remove(currentUser);
        communityRepository.save(community);

        List<UserCommunitySettings> userCommunitySettingsList = currentUser.getUserCommunitySettings();
        UserCommunitySettings userCommunitySettings = userCommunitySettingsList
                .stream()
                .filter(settings -> Objects.equals(settings.getCommunityId(), community.getId()))
                .findFirst()
                .orElseThrow(() -> new UserCommunitySettingsNotFoundException(currentUser.getUsername()));

        currentUser.getUserCommunitySettings().remove(userCommunitySettings);
        userRepository.save(currentUser);

        userCommunitySettingsRepository.delete(userCommunitySettings);
    }

    public List<CommunityResponse> getAllCommunitiesForUser(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("No user found with name - " + name)
        );

        return communityRepository
                .findAll()
                .stream()
                .filter(community -> community.getUsers().contains(user))
                .map(communityMapper::mapToCommunityResponse)
                .collect(Collectors.toList());
    }
}
