package org.education.network.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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
    private final ObjectMapper mapper;

    public JwtUtil(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
        this.mapper = new ObjectMapper();
    }

    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        this.jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build();
    }

    @SneakyThrows
    private String createToken(JwtDto jwtDto, long tokenValidity) {
        String subject = mapper.writeValueAsString(jwtDto);
        Claims claims = Jwts.claims().setSubject(subject);

        Instant tokenCreateTime = Instant.now().plus(tokenValidity, ChronoUnit.DAYS);
        Date validity = new Date(tokenCreateTime.toEpochMilli());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
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

    public Claims resolveClaims(String token) {
        try {
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (Exception ex) {
            throw new JwtException(ex);
        }

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

    public String resolveToken(String token) {
        if (token != null && token.startsWith(jwtProperties.getTokenPrefix())) {
            return token.substring(jwtProperties.getTokenPrefix().length());
        }
        return null;
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

    @SneakyThrows
    public JwtDto getJwtDto(String token) {
        token = resolveToken(token);
        String sub = resolveClaims(token).getSubject();
        return mapper.readValue(sub, JwtDto.class);
    }

}
