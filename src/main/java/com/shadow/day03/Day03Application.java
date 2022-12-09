package com.shadow.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName Day03Application
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/5 21:43
 * @Version 1.0
 **/
@SpringBootApplication
@Slf4j
public class Day03Application {
    /**
     * 解决prototype失效问题： 本质就是，E的依赖注入只注入一次，后面就不会进行注入了，但是解决本质是，将scope的注入延后到使用方法时
     * 1. 使用@Lazy到注入多例对象的字段上
     * 2. prototype中使用@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
     * 3. ObjectFactory<F1> f1, 从ObjectFactory中提取F1对象进行
     * 4. ApplicationContext 来提取对象
     * 总结： 前两种使用了代理，后两种未使用代理，性能较高
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Day03Application.class, args);
        E bean = context.getBean(E.class);
        log.debug(bean.getF1());
        log.debug(bean.getF1());
        log.debug(bean.getF1());
    }
}
