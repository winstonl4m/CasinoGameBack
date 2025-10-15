package com.CasinoGameBackend.CasinoGameJava.service;

import com.CasinoGameBackend.CasinoGameJava.dto.ProfileRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.ProfileResponseDto;

public interface IProfileService {

    ProfileResponseDto getProfile();


    ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto);
}
