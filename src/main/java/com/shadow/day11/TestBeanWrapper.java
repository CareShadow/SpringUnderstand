package com.shadow.day11;

import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;

/**
 * @ClassName TestBeanWrapper
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/5 19:49
 * @Version 1.0
 **/
public class TestBeanWrapper {
    public static void main(String[] args) {
        // 利用反射原理,为bean的属性赋值 通过set方法赋值
        MyBean target = new MyBean();
        BeanWrapperImpl wrapper = new BeanWrapperImpl(target);
        wrapper.setPropertyValue("a", "10");
        wrapper.setPropertyValue("b", "hello");
        wrapper.setPropertyValue("c", "1999/03/07");
        System.out.println(target);
    }

    static class MyBean {
        private int a;
        private String b;
        private Date c;

        public String getB() {
            return b;
        }

        public Date getC() {
            return c;
        }

        public void setB(String b) {
            this.b = b;
        }

        public void setC(Date c) {
            this.c = c;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }

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
