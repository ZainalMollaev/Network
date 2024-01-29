package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.security.auth.JwtUtil;
import org.education.network.dto.response.JwtDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtDto updateAccess(String subject) {
        return JwtDto.builder()
                .accessToken(jwtUtil.createAccessToken(subject))
                .refreshToken(userService.getRefreshTokenByEmail(subject))
                .build();
    }

    public JwtDto updateRefresh(String subject, String refreshToken) {
        refreshToken = jwtUtil.createRefreshToken(subject);
        userService.updateRefreshToken(subject, refreshToken);

        return JwtDto.builder()
                .accessToken(jwtUtil.createAccessToken(subject))
                .refreshToken(refreshToken)
                .build();
    }

}
