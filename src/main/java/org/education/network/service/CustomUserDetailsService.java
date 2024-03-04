package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDto userDto = userService.getUserDtoByEmail(email);
        List<String> roles = List.of("USER");

        return org.springframework.security.core.userdetails.User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(roles.get(0))
                .build();
    }

}
