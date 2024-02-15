package org.education.network.security.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.education.network.dto.response.JwtDto;
import org.education.network.util.JwtUtil;
import org.education.network.web.exceptions.AuthenticationNetworkException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            Claims claims = jwtUtil.resolveClaims(request);

            if(claims != null
                    && jwtUtil.validateClaims(claims)) {
                JwtDto jwtDto = mapper.readValue(claims.getSubject(), JwtDto.class);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(jwtDto.getUsername(), "", List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            throw new AuthenticationNetworkException(e.getMessage());
        }
    }

}
