package com.example.hellpyending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HellpyendingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellpyendingApplication.class, args);
    }

}
