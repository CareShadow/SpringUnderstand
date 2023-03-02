package com.shadow.day23;

import com.shadow.day21.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @ClassName Day23Application
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/3/2 20:43
 * @Version 1.0
 **/
public class Day23Application {
    // 1. @PostConstruct  初始化方法 实例化对象  2. @Bean(initMethod = "") 3. 实现initializingBean
    // AbstractBeanDefinition#initMethodName
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Day23Application.class);
        context.refresh();
        System.out.println("Spring 上下文启动后");
        // @Lazy在依赖查找的时候进行初始化方法
        User bean = context.getBean(User.class);
        context.close();
    }

    @Bean(initMethod = "initMethod")
    @Lazy
    public User user() {
        return new User();
    }


}
