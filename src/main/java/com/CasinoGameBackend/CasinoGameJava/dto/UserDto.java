package com.CasinoGameBackend.CasinoGameJava.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private Integer user_id;
    private String name;
    private String username;
    //private String password;
    private Integer dateOfBirth;
    private Integer balance;


}
