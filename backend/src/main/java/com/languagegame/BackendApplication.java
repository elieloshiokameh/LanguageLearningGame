package com.languagegame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
    @SpringBootApplication == @SpringBootConfiguration @EnableAutoConfiguration @ComponentScan
                                under the hood.
        but stupid IntelliJ gives errors not understanding that so yea ... both works though feel free to test
*/
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
//@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
