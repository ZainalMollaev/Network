package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subs")
@Tag(name = "SubscribeController", description = "action with subscription")
public class SubscribeController {

    private final ProfileService profileService;

    @Operation(summary = "add user subscription")
    @PutMapping("/subscribe/user")
    public ResponseEntity subscribe(@RequestParam("personEmail") String personEmail,
                                    @RequestParam("subscriptionEmail") String subscriptionEmail) {
        return profileService.subscribeUser(personEmail, subscriptionEmail);
    }

    @Operation(summary = "get all user subscriptions")
    @GetMapping("/subscriptions/user")
    public ResponseEntity getAllUserSubscriptions(@RequestParam("email") String email) {
        return profileService.getAllUserSubscriptions(email);
    }

}
