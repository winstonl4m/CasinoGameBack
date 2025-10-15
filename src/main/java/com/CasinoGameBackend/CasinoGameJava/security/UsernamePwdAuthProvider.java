package com.CasinoGameBackend.CasinoGameJava.security;

import com.CasinoGameBackend.CasinoGameJava.entity.Player;
import com.CasinoGameBackend.CasinoGameJava.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UsernamePwdAuthProvider implements AuthenticationProvider {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Player player = playerRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "User details cound not be found for the user: " + username
                )
        );
        if (passwordEncoder.matches(password, player.getPasswordHash())){
            return new UsernamePasswordAuthenticationToken(player, null, Collections.emptyList());
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
