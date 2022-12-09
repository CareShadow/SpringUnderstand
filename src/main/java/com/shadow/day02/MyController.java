package com.shadow.day02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName MyController
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/4 21:41
 * @Version 1.0
 **/
@RestController
public class MyController {
    @Lazy
    @Autowired
    private BeanForRequest beanForRequest;

    @Lazy
    @Autowired
    private BeanForSession beanForSession;

    @Lazy
    @Autowired
    private BeanForApplication beanForApplication;

    @GetMapping(value = "/test", produces = "text/html")
    public String test(HttpServletRequest request, HttpSession session) {
        ServletContext sc = request.getServletContext();
        String sb = "<ul>" +
                "<li>" + "request scope:" + beanForRequest + "</li>" +
                "<li>" + "session scope:" + beanForSession + "</li>" +
                "<li>" + "application scope:" + beanForApplication + "</li>" +
                "</ul>";
        return sb;
    }

}
