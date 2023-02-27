package com.shadow.day21;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @ClassName BeanDefinitionRegister
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/2/27 22:34
 * @Version 1.0
 **/

// 3. Import注册Config
@Import(BeanDefinitionRegister.Config.class)
public class BeanDefinitionRegister {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BeanDefinitionRegister.class);
        context.refresh();
        Map<String, Config> configBeans = context.getBeansOfType(Config.class);
        Map<String, User> userBeans = context.getBeansOfType(User.class);
        System.out.println(configBeans);
        System.out.println(userBeans);
        context.close();
    }

    // 2. Component注解注册Config
    @Component
    static class Config {
        // 1. Bean注解注册User
        @Bean(name = {"user", "lxl-user"})
        public User user() {
            User user = new User();
            user.setAge(18);
            user.setName("lxl");
            return user;
        }
    }

    public static void registerNameBean(String beanName, BeanDefinitionRegistry registry, BeanDefinition bean) {
        if(StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, bean);
        }else {
            registerBean(registry, bean);
        }
    }

    public static void registerBean(BeanDefinitionRegistry registry, BeanDefinition bean) {
        registerNameBean(null, registry, bean);
    }
}
