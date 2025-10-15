package com.CasinoGameBackend.CasinoGameJava.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {

    @NotNull(message = "Deposit amount is required")
    @Min(value = 1, message = "Deposit amount must be greater than 0")
    private Integer amount;






}
