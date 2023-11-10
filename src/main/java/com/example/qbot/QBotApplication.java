package com.example.qbot;

import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSimbot
@SpringBootApplication
public class QBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QBotApplication.class, args);
    }

}
