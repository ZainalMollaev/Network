package org.education.network.dao;

import lombok.RequiredArgsConstructor;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserProfileDao {

    private final UserProfileRepository repository;

    public UserProfile findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String email) {
        return repository.existsByPhoneNumber(email);
    }

    public UserProfile save(UserProfile userProfile) {
        return repository.save(userProfile);
    }

    public Page<UserProfile> findByEmailWithPage(String email, Pageable pageable) {
        return repository.findByEmailWithPage(email, pageable);
    }

    public List<UserProfileRepository.NameOnly> findProperSubscriptionsOrSubscribersByName(String username, String likePattern) {
        return repository.findProperSubscriptionsOrSubscribersByName(username, likePattern);
    }


}
