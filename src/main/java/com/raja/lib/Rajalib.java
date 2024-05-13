package com.raja.lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Rajalib {

	public static void main(String[] args) {
		SpringApplication.run(Rajalib.class, args);
	}

}
