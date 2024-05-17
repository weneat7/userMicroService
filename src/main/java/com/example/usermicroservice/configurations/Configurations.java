package com.example.usermicroservice.configurations;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Templates;

@Configuration
public class Configurations {
    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplateBuilder().build();
    }
}
