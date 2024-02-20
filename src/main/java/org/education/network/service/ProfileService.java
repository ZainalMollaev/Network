package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.util.ResponseEntityUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileService userProfileService;

    public ResponseEntity editUserProfile(UserProfileDto userProfileDto, String subject) {
        userProfileDto.setEmail(subject);
        userProfileService.saveUserProfileByEmail(userProfileDto);
        return ResponseEntityUtil.get(HttpStatus.OK, "Changes saved");
    }

    public ResponseEntity getUserProfile(String email) {
        return ResponseEntityUtil.get(HttpStatus.OK, userProfileService.getUserProfile(email));
    }

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
