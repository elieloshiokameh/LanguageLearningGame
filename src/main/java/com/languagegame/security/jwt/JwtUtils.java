package com.languagegame.security.jwt;

import com.languagegame.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

/*
This class Deals with JWT interactions with cookies
and vice versa.
*.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${languagegame.app.jwtSecret}")
    private String jwtSecret;

    @Value("${languagegame.app.jwtCookieName}")
    private String jwtCookie;

    @Value("${languagegame.app.jwtExpirationMs}")
    private Integer jwtExpirationMs;


    // Exceptions to catch according to JWT validation documentation
    public boolean validateJwt(String authToken) {
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token (JWT) is invalid: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token (JWT) has expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token (JWT) is not supported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Token (JWT) is empty, or incomplete: {}", e.getMessage());
        }

        return false;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromJwt(String jwt) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(jwt).getBody().getSubject();
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }

    public String generateJwtFromEmail(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Where 'Principal' in Spring Security refers to the currently
    // logged-in user.
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal){
        String jwt = generateJwtFromEmail(userPrincipal.getEmail());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
                .path("/")
                .maxAge(24*60*60) // 24hrs max
                .httpOnly(true).build();
        return cookie;
    }

    //empty cookie, to replace current cookie when user signs out etc.
    public ResponseCookie getCleanJwtCookie(){
        return ResponseCookie.from(jwtCookie, null).path("/").maxAge(0).build();
    }
}
