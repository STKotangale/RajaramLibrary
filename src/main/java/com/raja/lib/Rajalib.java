package com.raja.lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.raja.lib")
public class Rajalib {

    public static void main(String[] args) {
        SpringApplication.run(Rajalib.class, args);
    }

}
