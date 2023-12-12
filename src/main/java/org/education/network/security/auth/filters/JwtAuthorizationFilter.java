package org.education.network.security.auth.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.UserDto;
import org.education.network.service.UserService;
import org.education.network.security.auth.JwtUtil;
import org.education.network.dto.CommonResponse;
import org.education.network.dto.LoginRes;
import org.education.network.service.JsonServices;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final JsonServices json;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.resolveToken(request);

        if (accessToken == null) {

            String requestData = new ContentCachingRequestWrapper(request).getReader().lines().collect(Collectors.joining());
            UserDto user = (UserDto) json.getObject(requestData, UserDto.class);
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            String email = authentication.getName();

            accessToken = jwtUtil.createAccessToken(user);
            String refreshToken = jwtUtil.createRefreshToken(user);
            user.setRefreshToken(refreshToken);
            userService.updateRefreshToken(user);

            request.setAttribute("loginRes", CommonResponse.builder()
                    .hasErrors(false)
                    .body(
                            LoginRes.builder()
                            .email(email)
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build()
                    ).build()
                    .toString());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return !request.getRequestURI().equals("/auth/login");
    }

}