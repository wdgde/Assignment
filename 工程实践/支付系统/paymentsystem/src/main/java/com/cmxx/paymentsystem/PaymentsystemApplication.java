package com.cmxx.paymentsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.cmxx.database")
@EnableScheduling
public class PaymentsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsystemApplication.class, args);
    }

}
