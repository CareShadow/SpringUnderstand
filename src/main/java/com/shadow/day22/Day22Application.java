package com.shadow.day22;

import com.shadow.day22.factory.DefaultUserFactory;
import com.shadow.day22.factory.UserFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @ClassName Day22Application
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/3/1 20:51
 * @Version 1.0
 **/
public class Day22Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/bean03.xml");
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        // User user = context.getBean("userTest", User.class);
        // User user = context.getBean("userFactoryBean", User.class);
        // System.out.println(user);
        // demoServiceLoader();
        ServiceLoader loader = applicationContext.getBean("serviceLoaderFactoryBean", ServiceLoader.class);
        // demoServiceLoaderBeanFactory(loader);
        DefaultUserFactory bean = beanFactory.createBean(DefaultUserFactory.class);
        System.out.println(bean.createUser());
    }

    // ServiceLoader since 1.6 两者区别在于ServiceLoaderBeanFactory有多种实现会逐行输出
    public static void demoServiceLoader() {
        ServiceLoader<UserFactory> load = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        demoServiceLoaderBeanFactory(load);
    }

    public static void demoServiceLoaderBeanFactory(ServiceLoader serviceLoader) {
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UserFactory next = iterator.next();
            System.out.println(next.createUser());
        }
    }
}
