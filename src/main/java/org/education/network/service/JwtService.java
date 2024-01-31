package org.education.network.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.education.network.dto.response.JwtDto;
import org.education.network.security.auth.JwtUtil;
import org.education.network.dto.response.JwtResponseDto;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class JwtService {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private ObjectMapper mapper;

    public JwtService(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @SneakyThrows
    public JwtResponseDto updateAccess(Principal principal) {
        JwtDto jwtDto = mapper.readValue(principal.getName(), JwtDto.class);
        return JwtResponseDto.builder()
                .accessToken(jwtUtil.createAccessToken(jwtDto))
                .refreshToken(userService.getRefreshTokenByEmail(jwtDto.getUsername()))
                .build();
    }

    @SneakyThrows
    public JwtResponseDto updateRefresh(String subject) {
        JwtDto jwtDto = mapper.readValue(subject, JwtDto.class);
        String refreshToken = jwtUtil.createRefreshToken(jwtDto);
        userService.updateRefreshToken(subject, refreshToken);

        return JwtResponseDto.builder()
                .accessToken(jwtUtil.createAccessToken(jwtDto))
                .refreshToken(refreshToken)
                .build();
    }

}
