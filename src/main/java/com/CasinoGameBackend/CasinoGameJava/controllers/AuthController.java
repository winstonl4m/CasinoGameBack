package com.CasinoGameBackend.CasinoGameJava.controllers;

import com.CasinoGameBackend.CasinoGameJava.dto.LoginRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.LoginResponseDto;
import com.CasinoGameBackend.CasinoGameJava.dto.RegisterRequestDto;
import com.CasinoGameBackend.CasinoGameJava.dto.UserDto;
import com.CasinoGameBackend.CasinoGameJava.entity.Player;
import com.CasinoGameBackend.CasinoGameJava.repository.PlayerRepository;
import com.CasinoGameBackend.CasinoGameJava.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PlayerRepository playerRepository;
    //private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));
            var userDto = new UserDto();
            var loggedInUser = (Player) authentication.getPrincipal();
            BeanUtils.copyProperties(loggedInUser, userDto);
            String jwtToken = jwtUtil.generateJwtToken(authentication);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), userDto, jwtToken));

        } catch (BadCredentialsException e) {
            System.err.println("Bad credentials: " + e.getMessage());
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        } catch (AuthenticationException e) {
            System.err.println("Auth exception: " + e.getMessage());
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace(); // This will show the full stack trace
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }

    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody  RegisterRequestDto registerRequestDto){

            Optional<Player> existingPlayer = playerRepository.findByUsername
                    (registerRequestDto.getUsername());
            if(existingPlayer.isPresent()){
                Map<String, String> errors = Map.of("username", "Username is already taken");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
            }
            Player player = new Player();
            BeanUtils.copyProperties(registerRequestDto, player);
            player.setPasswordHash(passwordEncoder.encode(registerRequestDto.getPassword()));
            player.setBalance(100);
            playerRepository.save(player);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Registration successful");
    }

    private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status, String message){
        return ResponseEntity.status(status)
                .body(new LoginResponseDto(message, null, null));

    }





}
