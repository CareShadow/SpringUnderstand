package com.shadow.day11;

import org.springframework.beans.DirectFieldAccessor;

import java.util.Date;

/**
 * @ClassName TestSimpleConverter
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/5 19:35
 * @Version 1.0
 **/
public class TestFieldAccessor {
    public static void main(String[] args) {
        // 仅有类型转换的功能 直接在成员变量进行赋值
        MyBean target = new MyBean();
        DirectFieldAccessor wrapper = new DirectFieldAccessor(target);
        wrapper.setPropertyValue("a", "10");
        wrapper.setPropertyValue("b", "hello");
        wrapper.setPropertyValue("c", "1999/03/07");
        System.out.println(target);
    }

    static class MyBean {
        private int a;
        private String b;
        private Date c;

        @Override
        public String toString() {
            return "MyBean{" +
                    "a=" + a +
                    ", b='" + b + '\'' +
                    ", c=" + c +
                    '}';
        }
    }
}
