package com.CasinoGameBackend.CasinoGameJava.service;

import com.CasinoGameBackend.CasinoGameJava.dto.BetRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.BetResponseDto;

public interface BetService {

    BetResponseDto getProfile();

    BetResponseDto betPlay(BetRequestDto betRequestDto);
}
