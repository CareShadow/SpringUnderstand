package com.shadow.day15;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName Day15Application
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/10 20:17
 * @Version 1.0
 **/
public class Day15Application {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        // 调用控制器方法
//        System.out.println(context.getBean(WebConfig.MyControllerAdvice.class).toString());
        ServletInvocableHandlerMethod handlerMethod = new ServletInvocableHandlerMethod(
                context.getBean(WebConfig.MyController.class),
                WebConfig.MyController.class.getMethod("user")
        );
        // 数据的绑定与转换
        handlerMethod.setDataBinderFactory(new ServletRequestDataBinderFactory(null, null));
        // 参数名解析器
        handlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());
        // 参数解析器
        handlerMethod.setHandlerMethodArgumentResolvers(getArgumentResolvers(context));
        // 返回值解析器
        handlerMethod.setHandlerMethodReturnValueHandlers(getReturnValueResolvers(context));

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        handlerMethod.invokeAndHandle(new ServletWebRequest(request, response), new ModelAndViewContainer());

        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
        context.close();
    }


    // 参数解析器
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

    // 返回值解析器
    public static HandlerMethodReturnValueHandlerComposite getReturnValueResolvers(AnnotationConfigApplicationContext context) {
        HandlerMethodReturnValueHandlerComposite composite = new HandlerMethodReturnValueHandlerComposite();
        ConfigurableBeanFactory factory = context.getDefaultListableBeanFactory();
        composite.addHandlers(
                List.of(
                        // ResponseBodyMethodProcessor两个参数
                    new RequestResponseBodyMethodProcessor(List.of(new MappingJackson2HttpMessageConverter()),
                            List.of(context.getBean(WebConfig.MyControllerAdvice.class)))
                )
        );
        return composite;
    }
}
