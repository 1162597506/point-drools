package com.liuzhe.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Alan on 2018/8/3.
 */
@SpringBootApplication
@ComponentScan(basePackages ="com.liuzhe.drools")
@EnableCaching
public class DroolsSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroolsSpringbootApplication.class, args);
    }
}
