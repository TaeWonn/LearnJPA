package com.tae.tae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.tae")
@EnableCaching
public class TaeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaeApplication.class, args);
    }

}
