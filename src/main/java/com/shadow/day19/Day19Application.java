package com.shadow.day19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @ClassName Day19Application
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/1/16 16:46
 * @Version 1.0
 **/
@Configuration
public class Day19Application {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("1.演示获取Bean Definition源");
        // @Configuration注解的类, Xml文件
        SpringApplication  application= new SpringApplication(Day19Application.class);
        // 设值多个数据源
        application.setSources(Set.of("classpath:bean02.xml"));
        System.out.println("2.演示推断应用类型");
        Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduceFromClasspath.setAccessible(true);
        System.out.println("\t推断应用类型为"+deduceFromClasspath.invoke(null));
        System.out.println("3.演示ApplicationContext初始化器");
        application.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                if (applicationContext instanceof GenericApplicationContext gac) {
                    gac.registerBean("bean3", Bean3.class);
                }
            }
        });
        System.out.println("4.演示监听器与事件");
        application.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("事件类名"+event.getClass());
            }
        });
        System.out.println("5.演示主类推断");
        Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        deduceMainApplicationClass.setAccessible(true);
        System.out.println("主类"+deduceMainApplicationClass.invoke(application));
        ConfigurableApplicationContext context = application.run(args);
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        // context.refresh() 方法调用前, 容器可以通过初始化器自行添加一些BeanDefinition
        context.close();
    }

    static class Bean1 {

    }

    static class Bean2 {

    }

    static class Bean3 {

    }

    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
}
