package com.shadow.day22;

import com.shadow.day21.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Day22Application
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/3/1 20:51
 * @Version 1.0
 **/
public class Day22Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/bean03.xml");
        // User user = context.getBean("userTest", User.class);
        User user = context.getBean("userFactoryBean", User.class);
        System.out.println(user);
    }
}
