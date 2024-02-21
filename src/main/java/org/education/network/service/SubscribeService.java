package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.response.SubscriptionDto;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.web.exceptions.SameUserException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final UserProfileService userProfileService;

    public String subscribeUser(String personUsername, String subscriptionUsername) {
        userProfileService.subcribeUser(personUsername, subscriptionUsername);
        if(personUsername.equals(subscriptionUsername)) {
            throw new SameUserException("");
        }
        return "You successfully subscribed";
    }

    public List<SubscriptionDto> getAllUserSubscriptions(String email, Pageable pageable) {
        return userProfileService.getAllUserSubscribers(email, pageable);
    }

    public List<UserProfileRepository.NameOnly> findProperSubscriptionsOrSubscribersByName(String username, String likePattern) {
        return userProfileService.findProperSubscriptionsOrSubscribersByName(username, likePattern);
    }

}
