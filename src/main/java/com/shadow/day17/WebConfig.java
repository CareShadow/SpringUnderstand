package com.shadow.day17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/1/13 23:31
 * @Version 1.0
 **/
@Configuration
public class WebConfig {
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
        return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
    }

//    // 以Bean的名字作为路由路径 但Bean的名字必须 / 开头
//    @Bean
//    public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
//        return new BeanNameUrlHandlerMapping();
//    }
//
//    // 调用的控制器类必须实现Controller接口
//    @Bean
//    public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter() {
//        return new SimpleControllerHandlerAdapter();
//    }

    @Component
    static class MyHandlerMapping implements HandlerMapping {
        @Override
        public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
            Controller controller = bean.get(request.getRequestURI());
            return new HandlerExecutionChain(controller);
        }

        @Autowired
        private ApplicationContext context;
        private Map<String, Controller> bean;
        // 依赖注入在前,初始化在后
        @PostConstruct
        public void init() {
            bean = context.getBeansOfType(Controller.class)
                    .entrySet()
                    .stream()
                            .filter(e-> e.getKey().startsWith("/"))
                    .collect(Collectors.toMap(e -> e.getKey(), v -> v.getValue()));
            System.out.println(bean);
        }

        @Override
        public boolean usesPathPatterns() {
            return HandlerMapping.super.usesPathPatterns();
        }
    }

    @Component
    static class MyHandlerAdapter implements HandlerAdapter {
        @Override
        public boolean supports(Object handler) {
            return handler instanceof Controller;
        }

        @Override
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if(handler instanceof Controller controller) {
                controller.handleRequest(request, response);
            }
            return null;
        }

        @Override
        public long getLastModified(HttpServletRequest request, Object handler) {
            return -1;
        }
    }


    @Component("/c1")
    public static class Controller1 implements Controller {

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().println("this is c1");
            return null;
        }
    }

    @Component("c2")
    public static class Controller2 implements Controller {

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().println("this is c2");
            return null;
        }
    }

    @Component("/c3")
    public static class Controller3 implements Controller {

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().println("this is c3");
            return null;
        }
    }

    @Bean("/c4")
    public Controller controller() {
        return (request, response) -> {
            response.getWriter().println("this is c4");
            return null;
        };
    }
}
