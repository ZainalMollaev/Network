package org.education.network.security.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.education.network.dto.bd.UserDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.dto.response.JwtDto;
import org.education.network.dto.response.LoginRes;
import org.education.network.enumtypes.Role;
import org.education.network.properties.FilterProperties;
import org.education.network.service.UserService;
import org.education.network.util.JwtUtil;
import org.education.network.web.exceptions.AuthenticationAndAuthorizationNetworkException;
import org.education.network.web.exceptions.RequestBodyHandlerException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final FilterProperties properties;
    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {

        String accessToken = jwtUtil.resolveToken(request);

        if (accessToken == null) {

            UserDto user = toUser(request);

            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            } catch (RuntimeException e){
                throw new AuthenticationAndAuthorizationNetworkException("Invalid username or password");
            }

            //todo исправить хардкод в role
            JwtDto jwtDto = JwtDto.builder()
                    .username(user.getEmail())
                    .role(Role.USER)
                    .build();

            accessToken = jwtUtil.createAccessToken(jwtDto);
            String refreshToken = jwtUtil.createRefreshToken(jwtDto);
            user.setRefreshToken(refreshToken);
            userService.updateRefreshToken(user.getEmail(), refreshToken);
            request.setAttribute(properties.getAttributeName(), CommonResponse.builder()
                    .hasErrors(false)
                    .body(
                            LoginRes.builder()
                                    .email(user.getEmail())
                                    .accessToken(accessToken)
                                    .refreshToken(refreshToken)
                                    .build()
                    ).build()
                    .toString());
        }

        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            throw new AuthenticationAndAuthorizationNetworkException(e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().equals(properties.getLoginUrl());
    }

    private UserDto toUser(HttpServletRequest request) {
        try {
            String requestData = new ContentCachingRequestWrapper(request).getReader().lines().collect(Collectors.joining());
            return mapper.readValue(requestData, UserDto.class);

        } catch (IOException e) {
            throw new RequestBodyHandlerException(e);
        }
    }

}
