package com.CasinoGameBackend.CasinoGameJava.controllers;


import com.CasinoGameBackend.CasinoGameJava.entity.Game;
import com.CasinoGameBackend.CasinoGameJava.repository.GameRepository;
import com.CasinoGameBackend.CasinoGameJava.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/games")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class GameController {

    private final GameService gameService;


    @GetMapping
    public ResponseEntity<List<Game>> getGames(){
        List<Game> gameList = gameService.getGames();
        return ResponseEntity.ok().body(gameList);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable int gameId){
        Game theGame = gameService.getGame(gameId);
        return ResponseEntity.ok().body(theGame);

    }

}
