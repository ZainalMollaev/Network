package org.education.network.security.auth;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.education.network.db.model.dto.UserDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final JwtParser jwtParser;
    private final String secret_key = "mysecretkeymysecretkeymysecretkeymysecretkey";

    public JwtUtil(){
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    private String createToken(UserDto user, long tokenValidity) {

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        Date tokenCreateTime = new Date();
        Date validity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(tokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();

    }

    public String createAccessToken(UserDto user) {
        long accessTokenValidity = 24 * 60 * 60 * 1000;
        return createToken(user, accessTokenValidity);
    }

    public String createRefreshToken(UserDto user) {
        long refreshTokenValidity = 15 * 24 * 60 * 60 * 1000;
        return createToken(user, refreshTokenValidity);
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }

    }

    public String resolveToken(HttpServletRequest request) {

        String TOKEN_HEADER = "Authorization";
        String bearerToken = request.getHeader(TOKEN_HEADER);
        String TOKEN_PREFIX = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
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

}