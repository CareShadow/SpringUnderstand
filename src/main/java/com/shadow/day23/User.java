package com.shadow.day23;

import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;

/**
 * @ClassName User
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/3/3 21:51
 * @Version 1.0
 **/
public class User implements DisposableBean {
    private String name;
    private int age;

    public User() {}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy");
    }

    public void destroy() {
        System.out.println("DisposableBean");
    }

    public void initDestroy() {
        System.out.println("自定义destroy");
    }
}
