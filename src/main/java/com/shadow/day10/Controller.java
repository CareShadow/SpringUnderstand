package com.shadow.day10;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName Controller
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/4 19:45
 * @Version 1.0
 **/
public class Controller {
    public void test(
            @RequestParam("name1") String name1, //解决param参数名不匹配
            String name2,
            @RequestParam("age") int age,
            @RequestParam(name = "home", defaultValue = "${JAVA_HOME}") String home1,
            @RequestParam("file") MultipartFile file,
            @PathVariable("id") int id, // /test/{id};
            @RequestHeader("Content-Type") String header,
            @CookieValue("token") String token,
            @Value("${JAVA_HOME}") String home2,
            HttpServletRequest request, // /name=zhangsan&age=18
            User user2, // /name=zhangsan&age=18
            @RequestBody User user3 // json 请求体中的
    ) {
    }

    static class User {
        public String name;
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
