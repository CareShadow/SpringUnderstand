package com.shadow.day04;

import com.shadow.day04.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName Day04Application
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/5 22:34
 * @Version 1.0
 **/
@SpringBootApplication
public class Day04Application {
    /**
     * ajc增强，在javac文件编译其中进行修改class文件，不需要和applicationContext容器共同存在
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Day04Application.class, args);
        MyService service = context.getBean(MyService.class);
        service.foo();
        context.close();
    }
}
