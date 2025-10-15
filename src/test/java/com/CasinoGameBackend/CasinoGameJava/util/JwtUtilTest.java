package com.CasinoGameBackend.CasinoGameJava.util;

import com.CasinoGameBackend.CasinoGameJava.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    @Mock
    private Environment env;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtil jwtUtil;

    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player();
        testPlayer.setPlayerId(1);
        testPlayer.setUsername("testuser");
        testPlayer.setName("Test User");
        testPlayer.setBalance(1000);
    }

    @Test
    void testGenerateJwtToken_Success() {
        // Arrange
        when(env.getProperty("JWT_SECRET", "463c51c6ae99fa19378674864a42e211"))
                .thenReturn("463c51c6ae99fa19378674864a42e211");
        when(authentication.getPrincipal()).thenReturn(testPlayer);

        // Act
        String token = jwtUtil.generateJwtToken(authentication);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains(".")); // JWT tokens contain dots
    }
}
