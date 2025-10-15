package com.CasinoGameBackend.CasinoGameJava.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id", nullable = false)
    private Integer playerId;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Size(max = 500)
    @NotNull
    @Column(name = "password_hash", nullable = false, length = 500)
    private String passwordHash;

    @Min(value = 0)
    @NotNull
    @Column(name = "balance", nullable = false)
    private Integer balance;
}
