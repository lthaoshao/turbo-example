package com.didiglobal.turbo.example;

import com.didiglobal.turbo.engine.annotation.EnableTurboEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableTurboEngine
@SpringBootApplication
public class TurboExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurboExampleApplication.class, args);
    }

}
