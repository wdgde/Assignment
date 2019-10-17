package com.cmxx.upay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.cmxx.database")
@EnableScheduling
public class UpayApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpayApplication.class, args);
    }
}
