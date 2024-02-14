package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.response.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileService userProfileService;

    public ResponseEntity editUserProfile(UserProfileDto userProfileDto, String subject) {
        userProfileDto.setEmail(subject);
        userProfileService.saveUserProfileByEmail(userProfileDto);
        return ResponseEntity.ok(CommonResponse.builder()
                        .hasErrors(false)
                        .body("Changes saved")
                .build());
    }

    public ResponseEntity getUserProfile(String email) {
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body(userProfileService.getUserProfile(email))
                .build());
    }

    //todo нельзя подписываться на самого себя
    public ResponseEntity subscribeUser(String personEmail, String subscriptionEmail) {
        userProfileService.subcribeUser(personEmail, subscriptionEmail);
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body("ok")
                .build());
    }

    public ResponseEntity getAllUserSubscriptions(String email, Pageable pageable) {
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body(userProfileService.getAllUserSubscribers(email, pageable))
                .build());
    }

    //todo Сделать ResponseEntityUtil класс
    public ResponseEntity findProperSubscriptionsOrSubscribersByName(String username, String likePattern) {
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body(userProfileService.findProperSubscriptionsOrSubscribersByName(username, likePattern))
                .build());
    }

}
