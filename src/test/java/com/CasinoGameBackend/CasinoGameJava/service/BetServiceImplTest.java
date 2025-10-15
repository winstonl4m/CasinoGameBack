package com.CasinoGameBackend.CasinoGameJava.service;

import com.CasinoGameBackend.CasinoGameJava.dto.BetRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.BetResponseDto;
import com.CasinoGameBackend.CasinoGameJava.entity.Game;
import com.CasinoGameBackend.CasinoGameJava.entity.Player;
import com.CasinoGameBackend.CasinoGameJava.repository.GameRepository;
import com.CasinoGameBackend.CasinoGameJava.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BetServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameService gameService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private BetServiceImpl betService;

    private Player testPlayer;
    private Game testGame;
    private BetRequestDto betRequestDto;

    @BeforeEach
    void setUp() {
        testPlayer = new Player();
        testPlayer.setPlayerId(1);
        testPlayer.setUsername("testuser");
        testPlayer.setName("Test Player");
        testPlayer.setBalance(1000);

        testGame = new Game();
        testGame.setGame_id(1);
        testGame.setName("Test Game");
        testGame.setChance_of_winning(0.5);
        testGame.setWinning_multiplier(2.0);
        testGame.setMin_bet(10);
        testGame.setMax_bet(1000);

        betRequestDto = new BetRequestDto();
        betRequestDto.setAmount(100);
        betRequestDto.setGameId(1);
    }

    @Test
    void testGetProfile_Success() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("testuser");
            when(playerRepository.findByUsername("testuser")).thenReturn(Optional.of(testPlayer));

            // Act
            BetResponseDto result = betService.getProfile();

            // Assert
            assertNotNull(result);
            assertEquals(1, result.getPlayerId());
            assertEquals("testuser", result.getUsername());
            assertEquals("Test Player", result.getName());
            assertEquals(1000, result.getBalance());
        }
    }

    @Test
    void testGetProfile_UserNotFound() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("nonexistent");
            when(playerRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(UsernameNotFoundException.class, () -> betService.getProfile());
        }
    }

    @Test
    void testBetPlay_Success_PlayerWins() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class);
             MockedStatic<Math> mockedMath = mockStatic(Math.class)) {

            // Mock security context
            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("testuser");
            when(playerRepository.findByUsername("testuser")).thenReturn(Optional.of(testPlayer));

            // Mock game service
            when(gameService.getGame(1)).thenReturn(testGame);

            // Mock Math.random to return 0.3 (less than 0.5 win chance = player wins)
            mockedMath.when(Math::random).thenReturn(0.3);

            // Mock player save
            Player updatedPlayer = new Player();
            updatedPlayer.setPlayerId(1);
            updatedPlayer.setUsername("testuser");
            updatedPlayer.setName("Test Player");
            updatedPlayer.setBalance(1200); // 1000 + (100 * 2.0)
            when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

            // Act
            BetResponseDto result = betService.betPlay(betRequestDto);

            // Assert
            assertNotNull(result);
            assertEquals("Congratulations! You won!", result.getResult());
            assertEquals(1200, result.getBalance());
            verify(playerRepository).save(any(Player.class));
        }
    }

    @Test
    void testBetPlay_Success_PlayerLoses() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class);
             MockedStatic<Math> mockedMath = mockStatic(Math.class)) {

            // Mock security context
            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("testuser");
            when(playerRepository.findByUsername("testuser")).thenReturn(Optional.of(testPlayer));

            // Mock game service
            when(gameService.getGame(1)).thenReturn(testGame);

            // Mock Math.random to return 0.7 (greater than 0.5 win chance = player loses)
            mockedMath.when(Math::random).thenReturn(0.7);

            // Mock player save
            Player updatedPlayer = new Player();
            updatedPlayer.setPlayerId(1);
            updatedPlayer.setUsername("testuser");
            updatedPlayer.setName("Test Player");
            updatedPlayer.setBalance(900); // 1000 - 100
            when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

            // Act
            BetResponseDto result = betService.betPlay(betRequestDto);

            // Assert
            assertNotNull(result);
            assertEquals("Sorry, you lost this round.", result.getResult());
            assertEquals(900, result.getBalance());
            verify(playerRepository).save(any(Player.class));
        }
    }

    @Test
    void testBetPlay_GameNotFound() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("testuser");
            when(playerRepository.findByUsername("testuser")).thenReturn(Optional.of(testPlayer));
            when(gameService.getGame(1)).thenThrow(new RuntimeException("Game not found"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> betService.betPlay(betRequestDto));
        }
    }

    @Test
    void testBetPlay_PlayerNotFound() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("testuser");
            when(playerRepository.findByUsername("testuser")).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(UsernameNotFoundException.class, () -> betService.betPlay(betRequestDto));
        }
    }

    @Test
    void testBetPlay_WinChanceZero_PlayerAlwaysLoses() {
        // Arrange
        testGame.setChance_of_winning(0.0); // 0% win chance

        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class);
             MockedStatic<Math> mockedMath = mockStatic(Math.class)) {

            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("testuser");
            when(playerRepository.findByUsername("testuser")).thenReturn(Optional.of(testPlayer));
            when(gameService.getGame(1)).thenReturn(testGame);

            // Even with low random value, player should lose with 0% win chance
            mockedMath.when(Math::random).thenReturn(0.1);

            Player updatedPlayer = new Player();
            updatedPlayer.setPlayerId(1);
            updatedPlayer.setUsername("testuser");
            updatedPlayer.setName("Test Player");
            updatedPlayer.setBalance(900);
            when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

            // Act
            BetResponseDto result = betService.betPlay(betRequestDto);

            // Assert
            assertEquals("Sorry, you lost this round.", result.getResult());
            assertEquals(900, result.getBalance());
        }
    }

    @Test
    void testBetPlay_WinChanceOne_PlayerAlwaysWins() {
        // Arrange
        testGame.setChance_of_winning(1.0); // 100% win chance

        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class);
             MockedStatic<Math> mockedMath = mockStatic(Math.class)) {

            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("testuser");
            when(playerRepository.findByUsername("testuser")).thenReturn(Optional.of(testPlayer));
            when(gameService.getGame(1)).thenReturn(testGame);

            // Even with high random value, player should win with 100% win chance
            mockedMath.when(Math::random).thenReturn(0.9);

            Player updatedPlayer = new Player();
            updatedPlayer.setPlayerId(1);
            updatedPlayer.setUsername("testuser");
            updatedPlayer.setName("Test Player");
            updatedPlayer.setBalance(1200);
            when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

            // Act
            BetResponseDto result = betService.betPlay(betRequestDto);

            // Assert
            assertEquals("Congratulations! You won!", result.getResult());
            assertEquals(1200, result.getBalance());
        }
    }

    @Test
    void testBetPlay_HighMultiplier() {
        // Arrange
        testGame.setWinning_multiplier(5.0); // Higher multiplier

        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class);
             MockedStatic<Math> mockedMath = mockStatic(Math.class)) {

            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("testuser");
            when(playerRepository.findByUsername("testuser")).thenReturn(Optional.of(testPlayer));
            when(gameService.getGame(1)).thenReturn(testGame);

            // Mock winning scenario
            mockedMath.when(Math::random).thenReturn(0.3);

            Player updatedPlayer = new Player();
            updatedPlayer.setPlayerId(1);
            updatedPlayer.setUsername("testuser");
            updatedPlayer.setName("Test Player");
            updatedPlayer.setBalance(1500); // 1000 + (100 * 5.0)
            when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

            // Act
            BetResponseDto result = betService.betPlay(betRequestDto);

            // Assert
            assertEquals("Congratulations! You won!", result.getResult());
            assertEquals(1500, result.getBalance());
        }
    }
}
