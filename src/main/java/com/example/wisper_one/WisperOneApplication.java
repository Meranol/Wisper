package com.example.wisper_one;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan({"com.example.wisper_one.Login.mapper", "com.example.wisper_one.Login.usercode.mapper"})

public class WisperOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(WisperOneApplication.class, args);
    }
}
