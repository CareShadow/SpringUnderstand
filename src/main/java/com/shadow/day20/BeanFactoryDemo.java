package com.shadow.day20;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName BeanFactoryDemo
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/2/26 22:05
 * @Version 1.0
 **/
@Configuration
public class BeanFactoryDemo {
    public static void main(String[] args) {
        // XMLBeanFactory的实现
        // beanFactoryReader();
        // 注解注解的实现
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // BeanFactoryDemo作为配置类
        context.register(BeanFactoryDemo.class);
        // 启动容器上下文
        context.refresh();

        annotationConfigRender(context);
    }

    private static void annotationConfigRender(ApplicationContext context) {
        Object bean = context.getBean("bean");
        System.out.println(bean);
    }

    @Bean
    public com.shadow.day20.Bean bean() {
        com.shadow.day20.Bean bean = new com.shadow.day20.Bean();
        return bean;
    }

    private static void beanFactoryReader() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader render = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:bean02.xml";
        int beanNums = render.loadBeanDefinitions(location);
        System.out.println(beanNums);
    }
}
