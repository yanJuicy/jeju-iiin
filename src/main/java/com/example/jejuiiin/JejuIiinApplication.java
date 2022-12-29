package com.example.jejuiiin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JejuIiinApplication {

    public static void main(String[] args) {
        SpringApplication.run(JejuIiinApplication.class, args);
    }

}
