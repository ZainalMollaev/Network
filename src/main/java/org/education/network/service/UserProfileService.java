package org.education.network.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.request.SubscribeFilter;
import org.education.network.dto.response.SubscriptionDto;
import org.education.network.mapping.SubscriptionMapper;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.model.profile.Language;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.util.PredicateBuilder;
import org.education.network.web.exceptions.AuthenticationAndAuthorizationNetworkException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserProfileMapper profileMapper;
    private final SubscriptionMapper subscriptionMapper;
    private final EntityManager em;

    public UserProfile saveUserProfile(UserProfileDto user) {

        if(profileRepository.existsByEmail(user.getEmail())){
            throw new AuthenticationAndAuthorizationNetworkException("The email already exist!");
        }

        if(profileRepository.existsByPhoneNumber(user.getPhoneNumber())){
            throw new AuthenticationAndAuthorizationNetworkException("The number already exist!");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserProfile userAndProfile = profileMapper.toEntity(user);
        return profileRepository.save(userAndProfile);
    }

    public void editUserProfileByEmail(UserProfileDto userProfileDto) {
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

    public List<SubscriptionDto> getAllUserSubscribers(SubscribeFilter filter) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserProfile> query = cb.createQuery(UserProfile.class);
        Root<UserProfile> userProfile = query.from(UserProfile.class);
        Join<UserProfile, UserProfile> subscribes = userProfile.join("subscribes");
        Join<UserProfile, Language> languages = subscribes.join("languages");

        CriteriaQuery<UserProfile> select = query.select(subscribes);

        PredicateBuilder predicate = new PredicateBuilder(cb);
        predicate = predicate
                .like(filter.getLikePattern(), subscribes.get("personMain").get("name"), subscribes.get("personMain").get("lastname"))
                .in(filter.getLanguages(), languages.get("name"))
                .in(filter.getLocations(), subscribes.get("location"))
                .in(filter.getCompanies(), subscribes.get("lastjob").get("company"));

        select.where(predicate.getPredicate());

        List<UserProfile> resultList = em
                .createQuery(query)
                .setFirstResult((int) filter.getPageable().getOffset())
                .setMaxResults(filter.getPageable().getPageSize())
                .getResultList();


        return subscriptionMapper.toListDto(resultList);
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
