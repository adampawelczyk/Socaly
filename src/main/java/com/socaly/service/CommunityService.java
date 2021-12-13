package com.socaly.service;

import com.socaly.dto.CommunityDto;
import com.socaly.entity.Community;
import com.socaly.entity.User;
import com.socaly.exceptions.SocalyException;
import com.socaly.mapper.CommunityMapper;
import com.socaly.repository.CommunityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public CommunityDto save(CommunityDto communityDto) {
        User currentUser = authService.getCurrentUser();
        Community save = communityRepository.save(communityMapper.mapDtoToCommunity(communityDto, currentUser));
        communityDto.setId(save.getId());

        return communityDto;
    }

    @Transactional(readOnly = true)
    public List<CommunityDto> getAll() {
        return communityRepository.findAll().stream().map(communityMapper::mapCommunityToDto).collect(Collectors.toList());
    }

    public CommunityDto getCommunity(Long id) {
        Community community = communityRepository.findById(id).orElseThrow(
                () -> new SocalyException("No community found with id - " + id));

        return communityMapper.mapCommunityToDto(community);
    }
}
