package com.CasinoGameBackend.CasinoGameJava.service;

import com.CasinoGameBackend.CasinoGameJava.dto.ProfileRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.ProfileResponseDto;
import com.CasinoGameBackend.CasinoGameJava.entity.Player;
import com.CasinoGameBackend.CasinoGameJava.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements IProfileService {


    private final PlayerRepository playerRepository;


    @Override
    public ProfileResponseDto getProfile() {
        Player player = getAuthenticatedPlayer();
        return mapPlayerToProfileResponseDto(player);
    }

    @Override
    public ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto) {
        Player player = getAuthenticatedPlayer();

        BeanUtils.copyProperties(profileRequestDto, player);

        // Store previous balance for response
        Integer previousBalance = player.getBalance();

        // Add deposit amount to current balance
        Integer newBalance = previousBalance + profileRequestDto.getAmount();
        player.setBalance(newBalance);
        player = playerRepository.save(player);
        ProfileResponseDto profileResponseDto = mapPlayerToProfileResponseDto(player);
        return profileResponseDto;

    }





    private Player getAuthenticatedPlayer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return playerRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private ProfileResponseDto mapPlayerToProfileResponseDto(Player player){
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        BeanUtils.copyProperties(player, profileResponseDto);
        return profileResponseDto;
    }
}
