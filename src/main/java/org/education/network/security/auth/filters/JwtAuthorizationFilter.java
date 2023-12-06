package org.education.network.security.auth.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.education.network.db.model.dto.UserDto;
import org.education.network.db.service.UserService;
import org.education.network.security.auth.JwtUtil;
import org.education.network.security.model.response.CommonResponse;
import org.education.network.security.model.response.LoginRes;
import org.education.network.security.services.JsonServices;
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
@Slf4j
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

        LoginRes loginRes;


        if (accessToken == null && request.getRequestURI().contains("login")) {

            String requestData = new ContentCachingRequestWrapper(request).getReader().lines().collect(Collectors.joining());
            UserDto login = (UserDto) json.getObject(requestData, UserDto.class);

            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

            String email = authentication.getName();
            UserDto user = new UserDto(email, "", null);
            accessToken = jwtUtil.createAccessToken(user);
            String refreshToken = jwtUtil.createRefreshToken(user);
            user.setRefreshToken(refreshToken);
            userService.updateRefreshToken(user);
            loginRes = new LoginRes(email, accessToken, refreshToken);

            request.setAttribute("login_res", CommonResponse.builder()
                    .hasErrors(false)
                    .body(loginRes)
                    .build()
                    .toString());

        }

        filterChain.doFilter(request, response);

    }

}
