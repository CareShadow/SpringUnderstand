package com.shadow.day17;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * @ClassName Day17Application
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/1/13 23:31
 * @Version 1.0
 **/
public class Day17Application {
    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }
}
