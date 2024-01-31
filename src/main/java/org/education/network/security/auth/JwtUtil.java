package org.education.network.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.education.network.dto.response.JwtDto;
import org.education.network.properties.JwtProperties;
import org.education.network.web.exceptions.JwtException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    public JwtUtil(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
        this.objectMapper = new ObjectMapper();
    }

    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        this.jwtParser = Jwts.parser().setSigningKey(jwtProperties.getSecretKey());
    }

    @SneakyThrows
    private String createToken(JwtDto jwtDto, long tokenValidity) {
        String subject = objectMapper.writeValueAsString(jwtDto);
        Claims claims = Jwts.claims().setSubject(subject);

        Instant tokenCreateTime = Instant.now().plus(tokenValidity, ChronoUnit.DAYS);
        Date validity = new Date(tokenCreateTime.toEpochMilli());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public String createAccessToken(JwtDto jwtDto) {
        return createToken(jwtDto, jwtProperties.getAccessTokenValidity());
    }

    public String createRefreshToken(JwtDto jwtDto) {
        return createToken(jwtDto, jwtProperties.getRefreshTokenValidity());
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (Exception ex) {
            throw new JwtException(ex);
        }

    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getTokenHeader());
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getTokenPrefix())) {
            return bearerToken.substring(jwtProperties.getTokenPrefix().length());
        }
        return null;

    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getEmail(HttpServletRequest req){
        return resolveClaims(req).getSubject();
    }

}