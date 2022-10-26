package com.finx.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.finx.domain")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
