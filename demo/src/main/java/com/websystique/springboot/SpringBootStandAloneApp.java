package com.websystique.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;

@EnableAutoConfiguration
@ComponentScans(
        {
                @ComponentScan(value = "com.websystique.springboot"),
                @ComponentScan(value = "com.websystique.micro.service.account")
        })
@EntityScan(basePackages = "com.websystique.micro.service.account.domain")
//@SpringBootApplication(scanBasePackages={"com.websystique.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootStandAloneApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStandAloneApp.class, args);
    }
}
