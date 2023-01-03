package com.shadow.day09;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/26 22:06
 * @Version 1.0
 **/
@Configuration
@ComponentScan
@PropertySource("classpath:application.yml")
@EnableConfigurationProperties(WebMvcProperties.class)
public class WebConfig {
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        /**
         * 1. DispatchServlet初始化时机
         * 当load-on-startup未设置时,初始化时机会在Servlet的初始化中执行,也就是第一次请求
         * 当load-on-startup设置时,初始化和tomcat服务器一起执行
         **/
        return new DispatcherServlet();
    }

    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(
            DispatcherServlet dispatcherServlet, WebMvcProperties webMvcProperties) {
        DispatcherServletRegistrationBean registerBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        /**
         * 2. 一种良好的编程习惯
         * 将常量放到配置文件中去,在代码中不使用常量,只需改变配置文件中的常量即可
         * PropertySource用于指定配置文件的位置
         * EnableConfigurationProperties用于将配置文件中根据前缀进行分类,更好的找到value值
         **/
        registerBean.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
        return registerBean;
    }

    // 这里我们要return RequestMappingHandlerMapping 默认的实现不会注册到context容器中
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public MyRequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        TokenArgumentResolver argumentResolver = new TokenArgumentResolver();
        YmlReturnValueHandler returnValueHandler = new YmlReturnValueHandler();
        MyRequestMappingHandlerAdapter handlerAdapter = new MyRequestMappingHandlerAdapter();
        List<HandlerMethodArgumentResolver> customArgumentResolvers = new ArrayList<>();
        List<HandlerMethodReturnValueHandler> customMethodReturnValueHandler = new ArrayList<HandlerMethodReturnValueHandler>();
        customArgumentResolvers.add(argumentResolver);
        customMethodReturnValueHandler.add(returnValueHandler);
        handlerAdapter.setCustomArgumentResolvers(customArgumentResolvers);
        handlerAdapter.setCustomReturnValueHandlers(customMethodReturnValueHandler);
        return handlerAdapter;
    }
}
