package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.response.SubscriptionDto;
import org.education.network.mapping.SubscriptionMapper;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserProfileMapper profileMapper;
    private final SubscriptionMapper subscriptionMapper;

    public UserProfile saveUserProfile(UserProfileDto user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserProfile userAndProfile = profileMapper.toEntity(user);
        return profileRepository.save(userAndProfile);
    }

    public void saveUserProfileByEmail(UserProfileDto userProfileDto) {
        UserProfile userProfile = profileRepository.findByEmail(userProfileDto.getEmail());
        userProfile = profileMapper.partialUpdate(userProfileDto, userProfile);
        profileRepository.save(userProfile);
    }

    public UserProfile getUserProfile(String username) {
        return profileRepository.findByEmail(username);
    }

    public UserProfileDto getUserProfileDto(String username) {
        UserProfile userProfile = profileRepository.findByEmail(username);
        return profileMapper.toDto(userProfile);
    }

    @Transactional
    @Modifying
    public void subcribeUser(String personEmail, String subscriptionEmail) {
        UserProfile person = profileRepository.findByEmail(personEmail);
        UserProfile subscription = profileRepository.findByEmail(subscriptionEmail);
        person.addSubscription(subscription);
    }

    public List<SubscriptionDto> getAllUserSubscribers(String email, Pageable pageable) {

        Page<UserProfile> profile2 = profileRepository.findByEmailWithPage(email, pageable);
        List<SubscriptionDto> list = new ArrayList<>();

        for (UserProfile profile:
                profile2.getContent()) {
            SubscriptionDto subDto = subscriptionMapper.toSubDto(profile);
            list.add(subDto);
        }

        return list;
    }

    public List<UserProfileRepository.NameOnly> findProperSubscriptionsOrSubscribersByName(String username, String like) {

        String likePattern =
                new StringBuilder()
                        .append("%")
                        .append(like)
                        .append("%").toString();

        return profileRepository.findProperSubscriptionsOrSubscribersByName(username, likePattern);
    }
}
