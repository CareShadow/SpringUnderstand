package com.shadow.day11;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
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
        request.addParameter("date", "1999|01|02");
        request.addParameter("address.name", "西安");
        // 1. 用工厂默认的, 无转换功能
        User target = new User();
        InvocableHandlerMethod method = new InvocableHandlerMethod(new Controller(), Controller.class.getMethod("foo", WebDataBinder.class));

        // 2.用@InitBinder转换 PropertyEditorRegistry PropertyEditor
        // ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(List.of(method), null);

        // 3.用Initializer初始化器进行转换,优先级较低  InitBinder --> Initializer --> 符合转换器
        // FormattingConversionService service = new FormattingConversionService();
        // ConfigurableWebBindingInitializer webBindingInitializer = new ConfigurableWebBindingInitializer();
        // webBindingInitializer.setConversionService(service);
        // ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(List.of(method), webBindingInitializer);

        // 4.使用默认的ConversionService, FormattingConversionService 两个中实现 spring-core: DefaultConversionService, springboot: ApplicationConversionService
        DefaultFormattingConversionService service = new DefaultFormattingConversionService();
        ConfigurableWebBindingInitializer bindingInitializer = new ConfigurableWebBindingInitializer();
        bindingInitializer.setConversionService(service);
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null, bindingInitializer);
        WebDataBinder dataBinder = factory.createBinder(new ServletWebRequest(request), target, "user");
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));

        System.out.println(target);
    }

    static class Controller {
        @InitBinder
        public void foo(WebDataBinder binder) {
            binder.addCustomFormatter(new MySimpleDateFormat("用@InitBinder方式扩展的"));
        }
    }

    static class User {
        @DateTimeFormat(pattern = "yyyy|mm|dd")
        private Date date;
        private Address address;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
