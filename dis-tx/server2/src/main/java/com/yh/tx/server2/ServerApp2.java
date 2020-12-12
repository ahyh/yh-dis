package com.yh.tx.server2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = {"com.yh.tx.server2.dao"})
@SpringBootApplication(scanBasePackages = {"com.yh.global.tx"})
public class ServerApp2 {

    public static void main(String[] args) {
        SpringApplication.run(ServerApp2.class, args);
    }
}
