package org.education.network.service.authService;

import org.education.network.dto.bd.UserDto;
import org.education.network.service.dbService.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUserByUsernameTest() {
        UserDto userDto = UserDto.builder()
                .email("johndoe@mail.ru")
                .password("pass")
                .refreshToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQG1haWwucnUiLCJleHAiOjc5NDYzNTE1NzMyfQ.SOZE1CX7iId9ZtpC8b7YWWHIBWmE4LXwTF3a40BAacc")
                .build();
        Mockito.when(userService.getUserByEmail(anyString())).thenReturn(userDto);
        UserDetails user = User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .roles("USER")
                .build();

        assertEquals(user, service.loadUserByUsername("johndoe@mail.ru"));

    }
}