package com.CasinoGameBackend.CasinoGameJava.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BetResponseDto {
    private Integer playerId;
    private String name;
    private String username;
    private Integer balance;
    private String result;
}
