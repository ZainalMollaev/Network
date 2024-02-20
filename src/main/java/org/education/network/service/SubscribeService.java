package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.util.ResponseEntityUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final UserProfileService userProfileService;

    //todo нельзя подписываться на самого себя
    public ResponseEntity subscribeUser(String personEmail, String subscriptionEmail) {
        userProfileService.subcribeUser(personEmail, subscriptionEmail);
        return ResponseEntityUtil.get(HttpStatus.OK, "You successfully subscribed");
    }

    public ResponseEntity getAllUserSubscriptions(String email, Pageable pageable) {
        return ResponseEntityUtil.get(HttpStatus.OK, userProfileService.getAllUserSubscribers(email, pageable));
    }

    //todo Сделать ResponseEntityUtil класс
    public ResponseEntity findProperSubscriptionsOrSubscribersByName(String username, String likePattern) {
        return ResponseEntityUtil.get(HttpStatus.OK, userProfileService.findProperSubscriptionsOrSubscribersByName(username, likePattern));
    }

}
