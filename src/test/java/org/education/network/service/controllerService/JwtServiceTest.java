package org.education.network.service.controllerService;

import org.education.network.dto.bd.UserDto;
import org.education.network.dto.response.JwtDto;
import org.education.network.security.auth.JwtUtil;
import org.education.network.service.dbService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class JwtServiceTest {

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private UserService userService;
    @InjectMocks
    private JwtService jwtService;
    private JwtDto jwt;

    //Какой ещё нул?
    @BeforeEach
    void init() {
        Mockito.when(userService.getRefreshTokenByEmail(any())).thenReturn("refreshToken");
        Mockito.when(jwtUtil.createAccessToken(any())).thenReturn("accessToken");

        jwt = JwtDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
    }

    @Test
    void updateAccessTest() {

        assertEquals(jwt, jwtService.updateAccess(new UserDto()));
    }

    @Test
    void updateRefreshTest() {

        assertEquals(jwt, jwtService.updateRefresh(new UserDto()));
    }

}
