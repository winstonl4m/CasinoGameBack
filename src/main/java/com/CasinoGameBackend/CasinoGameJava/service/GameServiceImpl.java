package com.CasinoGameBackend.CasinoGameJava.service;

import com.CasinoGameBackend.CasinoGameJava.entity.Game;
import com.CasinoGameBackend.CasinoGameJava.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {


    private final GameRepository gameRepository;

    @Override
    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game getGame(int gameId) {
        Optional<Game> result = gameRepository.findById(gameId);

        Game theGame = null;

        if(result.isPresent()){
            theGame = result.get();
        } else {
            throw new RuntimeException("did not find game id: " + gameId);
        }

        return theGame;
    }

}
