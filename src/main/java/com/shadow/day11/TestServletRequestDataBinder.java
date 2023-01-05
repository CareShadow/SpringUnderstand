package com.shadow.day11;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;

import java.util.Date;

/**
 * @ClassName TestServletRequestDataBinder
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/5 20:54
 * @Version 1.0
 **/
public class TestServletRequestDataBinder {
    // 在web环境下的类型转换
    public static void main(String[] args) {
        MyBean myBean = new MyBean();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("a", "10");
        request.addParameter("b", "hello");
        request.addParameter("c", "1993/03/04");
        ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(myBean);
        dataBinder.initDirectFieldAccess();
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(myBean);
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
