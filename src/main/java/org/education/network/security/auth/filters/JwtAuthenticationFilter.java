package org.education.network.security.auth.filters;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.education.network.security.auth.JwtUtil;
import org.education.network.security.exceptions.AuthenticationNetworkException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            Claims claims = jwtUtil.resolveClaims(request);

            if(claims != null && jwtUtil.validateClaims(claims)) {
                String email = claims.getSubject();
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email, "", new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            throw new AuthenticationNetworkException(e.getMessage());
        }
    }

}

