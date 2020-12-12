package com.yh.global.tx.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig {

    /**
     * 所有的WebMvcConfigurerAdapter组件都会一起起作用
     */
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");
            }
        };
        return adapter;
    }
}
