package com.CasinoGameBackend.CasinoGameJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories
//@EntityScan
public class CasinoGameJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasinoGameJavaApplication.class, args);
	}

}
