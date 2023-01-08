package com.shadow.day13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @ClassName Day13Application
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/8 15:44
 * @Version 1.0
 **/
public class Day13Application {
    /*
        @InitBinder的来源有两个
        1. @ControllerAdvice 中的@InitBinder RequestMappingHandlerAdapter初始化时解析并记录
        2. @Controller中的@InitBinder RequestMappingHandlerAdapter第一次调用控制器方法解析并记录
     */
    private static final Logger log = LoggerFactory.getLogger(Day13Application.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        handlerAdapter.setApplicationContext(context);
        handlerAdapter.afterPropertiesSet();
        log.debug("刚开始");
        showBinderMethods(handlerAdapter);
        // 模拟调用控制器方法
        Method getDataBinderFactory = RequestMappingHandlerAdapter.class
                .getDeclaredMethod("getDataBinderFactory", HandlerMethod.class);
        getDataBinderFactory.setAccessible(true);
        log.debug("调用Controller2中的bar方法");
        getDataBinderFactory.invoke(handlerAdapter, new HandlerMethod(new WebConfig.Controller2(), WebConfig.Controller2.class.getMethod("bar", WebDataBinder.class)));
        log.debug("调用Controller3中的test方法");
        getDataBinderFactory.invoke(handlerAdapter, new HandlerMethod(new WebConfig.Controller3(), WebConfig.Controller3.class.getMethod("test", WebDataBinder.class)));
        showBinderMethods(handlerAdapter);
    }

    private static void showBinderMethods(RequestMappingHandlerAdapter handlerAdapter) throws NoSuchFieldException, IllegalAccessException {
        Field initBinderAdviceCache = RequestMappingHandlerAdapter.class.getDeclaredField("initBinderAdviceCache");
        initBinderAdviceCache.setAccessible(true);
        Map<ControllerAdviceBean, Set<Method>> globalMap = (Map<ControllerAdviceBean, Set<Method>>) initBinderAdviceCache.get(handlerAdapter);
        log.debug("全局的@InitBinder方法{}",
                globalMap.values().stream()
                        .flatMap(ms -> ms.stream().map(m -> m.getName()))
                        .collect(Collectors.toList())
        );
        Field initBinderCache = RequestMappingHandlerAdapter.class.getDeclaredField("initBinderCache");
        initBinderCache.setAccessible(true);
        Map<Class<?>, Set<Method>> controllerMap = (Map<Class<?>, Set<Method>>) initBinderCache.get(handlerAdapter);
        log.debug("Controller的@InitBinder方法{}",
                controllerMap.entrySet().stream()
                        .flatMap(e -> e.getValue().stream().map(v -> e.getKey().getSimpleName() + "," + v.getName()))
                        .collect(Collectors.toList())
        );
    }
}
