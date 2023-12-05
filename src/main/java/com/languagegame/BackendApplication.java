package com.languagegame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
    @SpringBootApplication == @SpringBootConfiguration @EnableAutoConfiguration @ComponentScan
                                under the hood.
        but stupid IntelliJ gives errors not understanding that so yea ... both works though feel free to test
*/
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class BackendApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
