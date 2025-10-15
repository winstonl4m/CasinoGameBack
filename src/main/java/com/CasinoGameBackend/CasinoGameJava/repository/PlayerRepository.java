package com.CasinoGameBackend.CasinoGameJava.repository;

import com.CasinoGameBackend.CasinoGameJava.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Optional<Player> findByUsername(String username);
}
