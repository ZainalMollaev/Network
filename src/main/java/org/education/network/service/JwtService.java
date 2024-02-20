package org.education.network.service;

import lombok.SneakyThrows;
import org.education.network.dto.response.JwtDto;
import org.education.network.dto.response.JwtResponseDto;
import org.education.network.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtService(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @SneakyThrows
    public JwtResponseDto updateAccess(String token) {
        JwtDto jwtDto = jwtUtil.getJwtDto(token);
        return JwtResponseDto.builder()
                .accessToken(jwtUtil.createAccessToken(jwtDto))
                .refreshToken(userService.getRefreshTokenByEmail(jwtDto.getUsername()))
                .build();
    }

    @SneakyThrows
    public JwtResponseDto updateRefresh(String token) {
        JwtDto jwtDto = jwtUtil.getJwtDto(token);
        String refreshToken = jwtUtil.createRefreshToken(jwtDto);
        userService.updateRefreshToken(jwtDto.getUsername(), refreshToken);

        return JwtResponseDto.builder()
                .accessToken(jwtUtil.createAccessToken(jwtDto))
                .refreshToken(refreshToken)
                .build();
    }

}
