package com.shadow.day21;

/**
 * @ClassName User
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/2/27 22:16
 * @Version 1.0
 **/
public class User {
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

    public static User createUser() {
        return new User();
    }
}
