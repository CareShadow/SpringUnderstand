package com.shadow.day14;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.util.List;

/**
 * @ClassName Day14Application
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/8 20:58
 * @Version 1.0
 **/
public class Day14Application {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        ServletInvocableHandlerMethod handlerMethod = new ServletInvocableHandlerMethod(new WebConfig.Controller1(),
                WebConfig.Controller1.class.getMethod("foo", WebConfig.User.class));
        ServletRequestDataBinderFactory dataBinderFactory = new ServletRequestDataBinderFactory(null, null);
        // 设置绑定数据工厂, 作用,通过参数解析器把对应的数据赋给参数
        handlerMethod.setDataBinderFactory(dataBinderFactory);
        // 设置参数名解析器
        handlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());
        // 设置参数解析器
        handlerMethod.setHandlerMethodArgumentResolvers(getArgumentResolvers(context));
        // 准备一个Model模型
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 准备一个MockHttp请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "张三");
        handlerMethod.invokeAndHandle(new ServletWebRequest(request), modelAndViewContainer);

        System.out.println(modelAndViewContainer.getModel());
    }

    public static HandlerMethodArgumentResolverComposite getArgumentResolvers(AnnotationConfigApplicationContext context) {
        HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        ConfigurableBeanFactory factory = context.getDefaultListableBeanFactory();
        composite.addResolvers(
                new RequestParamMethodArgumentResolver(factory, false),
                new PathVariableMethodArgumentResolver(),
                new RequestHeaderMethodArgumentResolver(factory),
                new ServletCookieValueMethodArgumentResolver(factory),
                new ExpressionValueMethodArgumentResolver(factory),
                new ServletRequestMethodArgumentResolver(),
                new ServletModelAttributeMethodProcessor(false),
                new RequestResponseBodyMethodProcessor(List.of(new MappingJackson2HttpMessageConverter())),
                new ServletModelAttributeMethodProcessor(true),
                new RequestParamMethodArgumentResolver(factory, true)
        );
        return composite;
    }
}
