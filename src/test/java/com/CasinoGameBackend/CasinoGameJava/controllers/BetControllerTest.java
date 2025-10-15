
package com.CasinoGameBackend.CasinoGameJava.controllers;

import com.CasinoGameBackend.CasinoGameJava.dto.BetRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.BetResponseDto;
import com.CasinoGameBackend.CasinoGameJava.service.BetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BetController.class)
class BetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BetService betService;

    @Autowired
    private ObjectMapper objectMapper;

    private BetRequestDto betRequestDto;
    private BetResponseDto betResponseDto;

    @BeforeEach
    void setUp() {
        betRequestDto = new BetRequestDto();
        betRequestDto.setAmount(100);
        betRequestDto.setGameId(1);

        betResponseDto = new BetResponseDto();
        betResponseDto.setPlayerId(1);
        betResponseDto.setName("Test Player");
        betResponseDto.setUsername("testuser");
        betResponseDto.setBalance(1200);
        betResponseDto.setResult("Congratulations! You won!");
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_Success_Win() throws Exception {
        // Arrange
        when(betService.betPlay(any(BetRequestDto.class))).thenReturn(betResponseDto);

        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerId").value(1))
                .andExpect(jsonPath("$.name").value("Test Player"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.balance").value(1200))
                .andExpect(jsonPath("$.result").value("Congratulations! You won!"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_Success_Lose() throws Exception {
        // Arrange
        betResponseDto.setBalance(900);
        betResponseDto.setResult("Sorry, you lost this round.");
        when(betService.betPlay(any(BetRequestDto.class))).thenReturn(betResponseDto);

        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(900))
                .andExpect(jsonPath("$.result").value("Sorry, you lost this round."));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_ServiceThrowsException() throws Exception {
        // Arrange
        when(betService.betPlay(any(BetRequestDto.class)))
                .thenThrow(new RuntimeException("Insufficient balance"));

        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequestDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_InvalidAmount_Negative() throws Exception {
        // Arrange
        betRequestDto.setAmount(-50);

        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_InvalidAmount_Zero() throws Exception {
        // Arrange
        betRequestDto.setAmount(0);

        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_NullAmount() throws Exception {
        // Arrange
        betRequestDto.setAmount(null);

        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_NullGameId() throws Exception {
        // Arrange
        betRequestDto.setGameId(null);

        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_EmptyRequestBody() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testPlaceBet_InvalidJson() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/bet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isBadRequest());
    }
}
