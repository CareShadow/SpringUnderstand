package com.shadow.day09;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @ClassName Day09Application
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/26 22:11
 * @Version 1.0
 **/
public class Day09Application {
    public static void main(String[] args) throws Exception {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        // 解析@RequestMapping 以及派生注解，生成器与控制器方法的映射关系，在初始化时就生成
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);

        // 获取映射结果
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        handlerMethods.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });

        // 请求来了， 获取控制 返回处理器执行链对象，执行链对象中包括了拦截器链  spring-boot-starter-test
        HandlerExecutionChain chain = handlerMapping.getHandler(new MockHttpServletRequest("GET", "/test1"));
        System.out.println(chain);

        MyRequestMappingHandlerAdapter handlerAdapter = context.getBean(MyRequestMappingHandlerAdapter.class);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test4");
        request.addHeader("token", "令牌");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain chain2 = handlerMapping.getHandler(request);
        System.out.println(chain2);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
        // 模拟的返回值, HandlerAdapter作用是调用路径对应的控制器方法,其内部有解析@RequestParam注解等组件
        ModelAndView modelAndView = handlerAdapter.invokeHandlerMethod(request, response, ((HandlerMethod) chain2.getHandler()));
        byte[] content = response.getContentAsByteArray();
        System.out.println(new String(content, StandardCharsets.UTF_8));
//        handlerAdapter.invokeHandlerMethod(request, response, ((HandlerMethod) chain2.getHandler()));

        // RequestAdapter中两大组件: 1. 参数解析器(@RequestParam等), 2. 返回值解析器(ModelAndView,String(视图),对象,Model)
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 参数解析器");
//        for (HandlerMethodArgumentResolver argumentResolver : handlerAdapter.getArgumentResolvers()) {
//            System.out.println(argumentResolver);
//        }
//
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 返回值解析器");
//        for (HandlerMethodReturnValueHandler returnValueHandler : handlerAdapter.getReturnValueHandlers()) {
//            System.out.println(returnValueHandler);
//        }
    }
}
