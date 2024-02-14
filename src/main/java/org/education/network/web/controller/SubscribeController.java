package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.service.ProfileService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscribes")
@Tag(name = "SubscribeController", description = "action with subscription")
public class SubscribeController {

    private final ProfileService profileService;

    @Operation(summary = "add user subscription")
    @PutMapping
    public ResponseEntity subscribe(@RequestParam("personEmail") String personEmail,
                                    Principal principal) {
        return profileService.subscribeUser(personEmail, principal.getName());
    }

    @Operation(summary = "get all user subscriptions")
    @GetMapping
    public ResponseEntity getAllUserSubscriptions(Principal principal, Pageable pageable) {
        return profileService.getAllUserSubscriptions(principal.getName(), pageable);
    }

    @Operation(summary = "find subscriptions or subscribers by string")
    @GetMapping
    public ResponseEntity findProperSubscriptionsOrSubscribersByString(Principal principal, @RequestParam String likePattern) {
        return profileService.findProperSubscriptionsOrSubscribersByName(principal.getName(), likePattern);
    }

}
