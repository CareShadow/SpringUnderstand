package com.shadow.day01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName StartApplication
 * @Description TODO
 * @Author 18451
 * @Date 2022/11/24 21:10
 * @Version 1.0
 **/
@SpringBootApplication
public class Day01Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Day01Application.class, args);
        context.close();
    }

    @Bean(initMethod = "init3")
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean(destroyMethod = "destory3")
    public Bean2 bean2() {
        return new Bean2();
    }
}
