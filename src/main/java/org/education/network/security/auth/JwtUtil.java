package org.education.network.security.auth;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.education.network.web.exceptions.JwtException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final String secret_key = "mysecretkeymysecretkeymysecretkeymysecretkey";
    private final long accessTokenValidity = 24 * 60 * 60 * 1000;
    private final long refreshTokenValidity = 15 * 24 * 60 * 60 * 1000;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    private final JwtParser jwtParser;

    public JwtUtil(){
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    private String createToken(String subject, long tokenValidity) {
        Claims claims = Jwts.claims().setSubject(subject);
        Date tokenCreateTime = new Date();
        Date validity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(tokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    public String createAccessToken(String subject) {
        return createToken(subject, accessTokenValidity);
    }

    public String createRefreshToken(String subject) {
        return createToken(subject, refreshTokenValidity);
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
        String bearerToken = request.getHeader(TOKEN_HEADER);
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

    public String getEmail(HttpServletRequest req){
        return resolveClaims(req).getSubject();
    }

}