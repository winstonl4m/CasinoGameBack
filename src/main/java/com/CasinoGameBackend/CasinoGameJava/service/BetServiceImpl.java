package com.CasinoGameBackend.CasinoGameJava.service;

import com.CasinoGameBackend.CasinoGameJava.dto.BetRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.BetResponseDto;
import com.CasinoGameBackend.CasinoGameJava.dto.ProfileResponseDto;
import com.CasinoGameBackend.CasinoGameJava.entity.Game;
import com.CasinoGameBackend.CasinoGameJava.entity.Player;
import com.CasinoGameBackend.CasinoGameJava.repository.GameRepository;
import com.CasinoGameBackend.CasinoGameJava.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService{

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final GameService gameService;

    @Override
    public BetResponseDto getProfile() {
        Player player = getAuthenticatedPlayer();
        return mapPlayerToBetResponseDto(player);
    }


    @Override
    public BetResponseDto betPlay(BetRequestDto betRequestDto) {
        Player player = getAuthenticatedPlayer();

        BeanUtils.copyProperties(betRequestDto, player);

        // Store previous balance for response
        Integer previousBalance = player.getBalance();

        Game theGame = gameService.getGame(betRequestDto.getGameId());
        Double win_chance = theGame.getChance_of_winning();
        Double win_multi = theGame.getWinning_multiplier();

        boolean isWin = simulateBet(win_chance);

        Integer newBalance;
        Integer winAmount = 0;

        if (isWin) {
            // Player wins: add (bet_amount * multiplier) to balance
            winAmount = (int) (betRequestDto.getAmount() * win_multi);
            newBalance = previousBalance + winAmount;
        } else {
            // Player loses: subtract bet amount from balance
            newBalance = previousBalance - betRequestDto.getAmount();
        }


        // Add deposit amount to current balance
        player.setBalance(newBalance);
        player = playerRepository.save(player);
        BetResponseDto betResponseDto = mapPlayerToBetResponseDto(player);
        betResponseDto.setResult(isWin ? "Congratulations! You won!" : "Sorry, you lost this round.");
        return betResponseDto;
    }



    private Player getAuthenticatedPlayer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return playerRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private BetResponseDto mapPlayerToBetResponseDto(Player player){
        BetResponseDto betResponseDto = new BetResponseDto();
        BeanUtils.copyProperties(player, betResponseDto);
        return betResponseDto;
    }

    private boolean simulateBet(Double winChance) {
        // Generate random number between 0.0 and 1.0
        double randomValue = Math.random();

        // Player wins if random value is less than or equal to win chance
        return randomValue <= winChance;
    }

}
