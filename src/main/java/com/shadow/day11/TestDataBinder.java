package com.shadow.day11;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

import java.util.Date;

/**
 * @ClassName TestDataBinder
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/5 20:24
 * @Version 1.0
 **/
public class TestDataBinder {
    public static void main(String[] args) {
        MyBean target = new MyBean();
        DataBinder dataBinder = new DataBinder(target);
        // 改为成员变量赋值
        dataBinder.initDirectFieldAccess();
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.add("a", "10");
        pvs.add("b", "hello");
        pvs.add("c", "1993/03/04");
        dataBinder.bind(pvs);
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
