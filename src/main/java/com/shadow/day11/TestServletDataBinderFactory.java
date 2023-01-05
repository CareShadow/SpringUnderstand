package com.shadow.day11;

import com.sun.tools.javac.util.List;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.Date;

/**
 * @ClassName TestServletDataBinderFactory
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/5 21:03
 * @Version 1.0
 **/
public class TestServletDataBinderFactory {
    public static void main(String[] args) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("date", "1999|01|02");
        request.setParameter("address.name", "西安");
        // 1. 用工厂默认的, 无转换功能
        User target = new User();
        InvocableHandlerMethod method = new InvocableHandlerMethod(new Controller(), Controller.class.getMethod("foo", WebDataBinder.class));
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(List.of(method), null);
        // 2.用@InitBinder转换 PropertyEditorRegistry PropertyEditor
        WebDataBinder dataBinder = factory.createBinder(new ServletWebRequest(request), target, "user");

        System.out.println(target);
    }

    static class Controller {
        @InitBinder
        public void foo(WebDataBinder binder) {
            binder.addCustomFormatter(new MySimpleDateFormat("用@InitBinder方式扩展的"));
        }
    }

    static class User {
        private Date date;
        private Address address;

        @Override
        public String toString() {
            return "User{" +
                    "date=" + date +
                    ", address=" + address +
                    '}';
        }
    }

    static class Address {
        private String name;

        @Override
        public String toString() {
            return "Address{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
