package com.CasinoGameBackend.CasinoGameJava.controllers;


import com.CasinoGameBackend.CasinoGameJava.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test")
public class UserController {

    @PostMapping("/register")
    public String createUser(@RequestBody UserDto userDto){
        System.out.println(userDto);
        return "user created";
    }

    @GetMapping("/hello")
    public String getUser(){
        return "user created";
    }


//    @PostMapping("/request-entity")
//    public String createUserWithEntity(RequestEntity<UserDto> requestEntity){
//        HttpHeaders header = requestEntity.getHeaders();
//        UserDto userDto= requestEntity.getBody();
//        System.out.println(userDto);
//        return "user created";
//    }
}
