package com.shadow.day14;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName Day14Application
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/8 20:58
 * @Version 1.0
 **/
public class Day14Application {
    // @ModelAttribute 和 @InitBinder 在Controller和ControllerAdvice一样

    /*
        a. MessageConverter的作用, @ResponseBody是返回值处理器解析的,但具体转换工作是MessageConverter实现的
        b. MediaType
            - 首先看@RequestMapping上有没有指定
            - 其次看request的Accept头有没有指定
            - 按MessageConverter的顺序决定
     */
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

        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setApplicationContext(context);
        adapter.afterPropertiesSet();
        // 准备一个MockHttp请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "张三");

        // 准备一个模型工厂方法 用来解析@ModelAttribute用作方法上的, 参数上由参数解析器ServletModelAttributeMethodProcessor来进行解析
        Method getModelFactory = RequestMappingHandlerAdapter.class.getDeclaredMethod("getModelFactory", HandlerMethod.class, WebDataBinderFactory.class);
        getModelFactory.setAccessible(true);
        ModelFactory modelFactory = (ModelFactory) getModelFactory.invoke(adapter, handlerMethod, dataBinderFactory);
        modelFactory.initModel(new ServletWebRequest(request), modelAndViewContainer, handlerMethod);

        // handlerMethod因该是一个调用控制器的类
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
