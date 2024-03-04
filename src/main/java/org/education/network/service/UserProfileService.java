package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dao.UserProfileDao;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.response.SubscriptionDto;
import org.education.network.mapping.SubscriptionMapper;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.web.exceptions.AuthenticationAndAuthorizationNetworkException;
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
    private final UserProfileDao profileDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserProfileMapper profileMapper;
    private final SubscriptionMapper subscriptionMapper;

    public UserProfile saveUserProfile(UserProfileDto user) {

        if(profileDao.existsByEmail(user.getEmail())){
            throw new AuthenticationAndAuthorizationNetworkException("The email already exist!");
        }

        if(profileDao.existsByPhoneNumber(user.getPhoneNumber())){
            throw new AuthenticationAndAuthorizationNetworkException("The number already exist!");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserProfile userAndProfile = profileMapper.toEntity(user);
        return profileDao.save(userAndProfile);
    }

    public void editUserProfileByEmail(UserProfileDto userProfileDto) {
        UserProfile userProfile = profileDao.findByEmail(userProfileDto.getEmail());
        userProfile = profileMapper.partialUpdate(userProfileDto, userProfile);
        profileRepository.save(userProfile);
    }

    public UserProfile getUserProfile(String username) {
        return profileDao.findByEmail(username);
    }

    public UserProfileDto getUserProfileDto(String username) {
        UserProfile userProfile = profileDao.findByEmail(username);
        return profileMapper.toDto(userProfile);
    }

    @Transactional
    @Modifying
    public void subcribeUser(String personEmail, String subscriptionEmail) {
        UserProfile person = profileDao.findByEmail(personEmail);
        UserProfile subscription = profileDao.findByEmail(subscriptionEmail);
        person.addSubscription(subscription);
    }

    public List<SubscriptionDto> getAllUserSubscribers(String email, Pageable pageable) {

        Page<UserProfile> profile2 = profileDao.findByEmailWithPage(email, pageable);
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

        return profileDao.findProperSubscriptionsOrSubscribersByName(username, likePattern);
    }

    public String editUserProfile(UserProfileDto userProfileDto, String subject) {
        userProfileDto.setEmail(subject);
        editUserProfileByEmail(userProfileDto);
        return "Changes saved";
    }
}
