package com.shadow.day19;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/1/16 16:46
 * @Version 1.0
 **/
public class WebConfig {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 添加app监听器
        SpringApplication app = new SpringApplication();
        // 当事件发生时, 事件监听器会监听到并作出响应的措施
        app.addListeners(e -> System.out.println(e.getClass()));

        // 获取事件发送器实现类名
        // SpringApplicationRunListener是一个接口, 它的实现类是在spring.factory文件中,
        // SpringFactoriesLoader Spring官方提供的一个工具 用于寻找spring.factory文件键值对
        List<String> names = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, WebConfig.class.getClassLoader());
        for (String name : names) {
            System.out.println(name);
            Class<?> clazz = Class.forName(name);
            Constructor<EventPublishingRunListener> constructor = (Constructor<EventPublishingRunListener>) clazz.getConstructor(SpringApplication.class, String[].class);
            EventPublishingRunListener publisher = constructor.newInstance(app, args);
            DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();
            publisher.starting(bootstrapContext); // spring boot 开始启动
            publisher.environmentPrepared(bootstrapContext, new StandardEnvironment()); // 环境信息准备完毕
            GenericApplicationContext context = new GenericApplicationContext();
            publisher.contextPrepared(context); // 在spring容器创建, 并调用初始化器之后,发送此事件
            publisher.contextLoaded(context); // 所有Bean Definition 加载完毕
            context.refresh();
            publisher.started(context); // spring容器初始化完成(refresh 方法调用完毕)
            publisher.running(context); // spring容器启动完毕
            publisher.failed(context, new Exception("出差了")); // spring启动出错
        }
    }
}
