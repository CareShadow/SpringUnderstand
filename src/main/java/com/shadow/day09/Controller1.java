package com.shadow.day09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.Yaml;


/**
 * @ClassName Controller1
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/3 19:52
 * @Version 1.0
 **/
@Controller
public class Controller1 {
    private static final Logger log = LoggerFactory.getLogger(Controller1.class);

    @GetMapping("/test1")
    public ModelAndView test1() throws Exception{
        log.debug("test1()");
        return null;
    }

    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name) {
        log.debug("test2({})", name);
        return null;
    }

    @PutMapping("/test3")
    public ModelAndView test3(@Token String token) {
        log.debug("test3({})", token);
        return null;
    }

    @RequestMapping("/test4")
    @Yml
    public User test4() {
        log.debug("test4");
        return new User("张三", 18);
    }

    public static class User {
        public String name;
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {
        String str = new Yaml().dump(new User("张三", 18));
        System.out.println(str);
    }
}
