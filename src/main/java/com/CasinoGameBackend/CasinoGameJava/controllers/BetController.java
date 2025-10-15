package com.CasinoGameBackend.CasinoGameJava.controllers;


import com.CasinoGameBackend.CasinoGameJava.dto.BetRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.BetResponseDto;
import com.CasinoGameBackend.CasinoGameJava.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bet")
@RequiredArgsConstructor
public class BetController {


    private final BetService betService;


    @PostMapping
    public ResponseEntity<BetResponseDto> placeBet(@RequestBody BetRequestDto betRequestDto){
        BetResponseDto betResponseDto = betService.betPlay(betRequestDto);
        return ResponseEntity.ok(betResponseDto);

    }
}
