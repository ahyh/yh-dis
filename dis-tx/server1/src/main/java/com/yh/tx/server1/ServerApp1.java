package com.yh.tx.server1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.yh.global.tx"})
@MapperScan(value = "com.yh.tx.server1.dao")
@SpringBootApplication(scanBasePackages = {"com.yh.global.tx"})
public class ServerApp1 {

    public static void main(String[] args) {
        SpringApplication.run(ServerApp1.class, args);
    }
}
