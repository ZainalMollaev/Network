package org.education.network.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.education.network.dto.response.JwtDto;
import org.education.network.enumtypes.Roles;
import org.education.network.properties.JwtProperties;
import org.education.network.web.exceptions.JwtException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

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

    private String createToken(String subject, long tokenValidity) {
        Claims claims = Jwts.claims().setSubject(subject);

        Instant tokenCreateTime = Instant.now().plus(tokenValidity, ChronoUnit.DAYS);
        Date validity = new Date(tokenCreateTime.toEpochMilli());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    private String createToken(String subject, List<Roles> roles, long tokenValidity) {

        JwtDto jwtDto = JwtDto.builder()
                .username(subject)
                .roles(roles)
                .build();

        Claims claims = Jwts.claims().setSubject(subject);

        Instant tokenCreateTime = Instant.now().plus(tokenValidity, ChronoUnit.DAYS);
        Date validity = new Date(tokenCreateTime.toEpochMilli());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public String createAccessToken(String subject) {
        return createToken(subject, jwtProperties.getAccessTokenValidity());
    }

    public String createRefreshToken(String subject) {
        return createToken(subject, jwtProperties.getRefreshTokenValidity());
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