package org.education.network.security.services;

import lombok.RequiredArgsConstructor;
import org.education.network.db.model.dto.UserDto;
import org.education.network.db.service.UserService;
import org.education.network.security.auth.JwtUtil;
import org.education.network.security.auth.dto.JwtDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtControllerService {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtDto updateAccess(UserDto userDto) {

        return JwtDto.builder()
                .accessToken(jwtUtil.createAccessToken(userDto))
                .refreshToken(userService.getRefreshTokenByEmail(userDto))
                .build();

    }

    public JwtDto updateRefresh(UserDto userDto) {

        String refreshToken = jwtUtil.createRefreshToken(userDto);
        userDto.setRefreshToken(refreshToken);
        userService.updateRefreshToken(userDto);

        return JwtDto.builder()
                .accessToken(jwtUtil.createAccessToken(userDto))
                .refreshToken(refreshToken)
                .build();

    }

}
