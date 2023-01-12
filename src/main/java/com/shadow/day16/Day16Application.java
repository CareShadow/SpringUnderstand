package com.shadow.day16;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @ClassName Day16Application
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/12 21:53
 * @Version 1.0
 **/
public class Day16Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        handlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            System.out.println(requestMappingInfo+"  " + handlerMethod);
        });
    }
}
