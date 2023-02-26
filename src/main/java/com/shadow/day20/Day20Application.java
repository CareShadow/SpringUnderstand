package com.shadow.day20;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Day20Application
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/2/24 22:24
 * @Version 1.0
 **/
public class Day20Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:bean02.xml");
        // extracted(beanFactory);

        // 延迟查找Bean
        Bean bean = (Bean) beanFactory.getBean("bean");
        System.out.println(bean.getObjectFactory().getObject() == beanFactory);

    }

    private static void extracted(ClassPathXmlApplicationContext beanFactory) {
        ObjectFactory objectFactory = (ObjectFactory) beanFactory.getBean("objectFactory");
        Object object = objectFactory.getObject();
        System.out.println(object);
    }
}

