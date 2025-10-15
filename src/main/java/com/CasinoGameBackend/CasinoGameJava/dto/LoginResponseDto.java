package com.CasinoGameBackend.CasinoGameJava.dto;

public record LoginResponseDto(String message, UserDto user, String jwtToken) {
}
