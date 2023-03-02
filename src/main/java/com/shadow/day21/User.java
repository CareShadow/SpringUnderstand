package com.shadow.day21;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @ClassName User
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/2/27 22:16
 * @Version 1.0
 **/
public class User implements InitializingBean {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct 初始化");
    }

    public void initMethod() {
        System.out.println("initMethod 初始化");
    }

    public static User createUser() {
        return new User();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet 初始化");
    }
}
