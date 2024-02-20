package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.service.SubscribeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final SubscribeService subscribeService;

    @Operation(summary = "add user subscription")
    @PutMapping
    public ResponseEntity subscribe(@RequestParam("personEmail") String personEmail,
                                    Principal principal) {
        return subscribeService.subscribeUser(personEmail, principal.getName());
    }

    @Operation(summary = "get all user subscriptions")
    @GetMapping
    public ResponseEntity getAllUserSubscriptions(Principal principal, Pageable pageable) {
        return subscribeService.getAllUserSubscriptions(principal.getName(), pageable);
    }

    @Operation(summary = "find subscriptions or subscribers by string")
    @GetMapping("/{likePattern}")
    public ResponseEntity findProperSubscriptionsOrSubscribersByString(Principal principal, @PathVariable String likePattern) {
        return subscribeService.findProperSubscriptionsOrSubscribersByName(principal.getName(), likePattern);
    }

}
