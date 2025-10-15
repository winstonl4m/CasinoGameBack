package com.CasinoGameBackend.CasinoGameJava.service;

import com.CasinoGameBackend.CasinoGameJava.entity.Game;
import com.CasinoGameBackend.CasinoGameJava.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game testGame1;
    private Game testGame2;

    @BeforeEach
    void setUp() {
        testGame1 = new Game();
        testGame1.setGame_id(1);
        testGame1.setName("Blackjack");
        testGame1.setChance_of_winning(0.48);

        testGame2 = new Game();
        testGame2.setGame_id(2);
        testGame2.setName("Roulette");
        testGame2.setChance_of_winning(0.47);
    }

    @Test
    void testGetGames_Success() {
        // Arrange
        List<Game> mockGames = Arrays.asList(testGame1, testGame2);
        when(gameRepository.findAll()).thenReturn(mockGames);

        // Act
        List<Game> result = gameService.getGames();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Blackjack", result.get(0).getName());
        assertEquals("Roulette", result.get(1).getName());
    }

    @Test
    void testGetGame_Success() {
        // Arrange
        when(gameRepository.findById(1)).thenReturn(Optional.of(testGame1));

        // Act
        Game result = gameService.getGame(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getGame_id());
        assertEquals("Blackjack", result.getName());
        assertEquals(0.48, result.getChance_of_winning());
    }

    @Test
    void testGetGame_NotFound() {
        // Arrange
        when(gameRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameService.getGame(999);
        });
        assertEquals("did not find game id: 999", exception.getMessage());
    }
}
