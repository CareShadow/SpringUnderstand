package com.shadow.day14;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/8 20:58
 * @Version 1.0
 **/
@Configuration
@ComponentScan
public class WebConfig {
    @ControllerAdvice
    static class MyControllerAdvice {
        @ModelAttribute("a")
        public String aa() {
            return "aa";
        }
    }

    @Controller
    static class Controller1 {
        @ModelAttribute("b")
        public String aa() {
            return "bb";
        }
        // 用于解决返回值解析器的缺失
        @ResponseStatus(HttpStatus.OK)
        public ModelAndView foo(@ModelAttribute("u") User user) {
            System.out.println(user);
            return null;
        }
    }

    static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
