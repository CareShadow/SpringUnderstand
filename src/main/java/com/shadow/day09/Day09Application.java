package com.shadow.day09;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * @ClassName Day09Application
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/26 22:11
 * @Version 1.0
 **/
public class Day09Application {
    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

    }
}
