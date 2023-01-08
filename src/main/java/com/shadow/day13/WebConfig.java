package com.shadow.day13;

import com.shadow.day11.MySimpleDateFormat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/8 15:44
 * @Version 1.0
 **/
@Configuration
@ComponentScan
public class WebConfig {
    @ControllerAdvice
    static class Controller1 {
        @InitBinder
        public void foo(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MySimpleDateFormat("Controller1-->foo-->InitBinder"));
        }
    }

    @Controller
    static class Controller2 {
        @InitBinder
        public void bar(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MySimpleDateFormat("Controller2-->bar-->InitBinder"));
        }
    }

    @Controller
    static class  Controller3 {
        @InitBinder
        public void test(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MySimpleDateFormat("Controller3-->bar-->InitBinder"));
        }
    }
}
