package com.CasinoGameBackend.CasinoGameJava.util;

import com.CasinoGameBackend.CasinoGameJava.constants.ApplicationConstants;
import com.CasinoGameBackend.CasinoGameJava.entity.Player;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;
    public String generateJwtToken(Authentication authentication) {
        String jwt= "";
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Player fetchedUser = (Player) authentication.getPrincipal();
        jwt = Jwts.builder().issuer("CasinoGame").subject("JWT Token")
                .claim("name", fetchedUser.getName())
                .claim("username", fetchedUser.getUsername())
                .claim("balance", fetchedUser.getBalance())
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date((new java.util.Date()).getTime() + 1000 * 60 * 60 * 10))
                .signWith(secretKey).compact();

        return jwt;

    }
}
