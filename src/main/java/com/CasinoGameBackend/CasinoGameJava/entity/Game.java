package com.CasinoGameBackend.CasinoGameJava.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    private Integer game_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "chance_of_winning", nullable = false)
    private Double chance_of_winning;

    @Column(name = "winning_multiplier", nullable = false)
    private Double winning_multiplier;

    @Column(name = "max_bet", nullable = false)
    private Integer max_bet;

    @Column(name = "min_bet", nullable = false)
    private Integer min_bet;


}
