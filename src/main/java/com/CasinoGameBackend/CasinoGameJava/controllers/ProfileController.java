package com.CasinoGameBackend.CasinoGameJava.controllers;

import com.CasinoGameBackend.CasinoGameJava.dto.ProfileRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.ProfileResponseDto;
import com.CasinoGameBackend.CasinoGameJava.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final IProfileService iProfileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(){
        ProfileResponseDto responseDto = iProfileService.getProfile();
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(
            @RequestBody @Validated ProfileRequestDto profileRequestDto){
        ProfileResponseDto responseDto = iProfileService.updateProfile(profileRequestDto);
        return ResponseEntity.ok(responseDto);

    }
}
