package com.yh.tx.server2.config;

import com.yh.global.tx.util.MyRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Server2Config {

    @Bean
    public MyRestTemplate restTemplate(){
        return new MyRestTemplate();
    }
}
