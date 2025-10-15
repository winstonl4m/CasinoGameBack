package com.CasinoGameBackend.CasinoGameJava.service;


import com.CasinoGameBackend.CasinoGameJava.entity.Game;

import java.util.List;

public interface GameService {

    List<Game> getGames();

    Game getGame(int game_id);

}
