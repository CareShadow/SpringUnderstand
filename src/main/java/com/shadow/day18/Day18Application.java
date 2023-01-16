package com.shadow.day18;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * @ClassName Day18Application
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/1/16 10:41
 * @Version 1.0
 **/
public class Day18Application {
    /**
     * 小编
     * a。 HandlerMapping 负责建立请求与控制器之间的映射关系
     *      RequestMappingHandlerMapping (与 @RequestMapping 匹配)
     *      WelcomePageHandlerMapping (/)
     *      BeanNameUrTHandlerMapping(与 bean 的名字匹虎 以/ 开头)
     *      RouterFunctionMapping(函数数式 RequestPredicate， HandlerFunction)
     *      SimpleUrLHandlerMapping(静态贷源 通配符 /** /img/**)
     *      之闻也会有顺序问题，boot 中默认顺序如上
     * b， HandlerAdapter 负责实现对各种各样的 handler 的适配调用
     *      RequestMappingHandlerAdapter 处理: @RequestMapping方法
     *          参数解析器、返回值处理器体现了组合模式
     *      SimpleControllerHandlerAdapter 处理: Controller 接口
     *      HandlerFunctionAdapter 处理: HandlerFunction 离数式接口
     *      HttpRequestHandlerAdapter 处理，HttpRequestHandler接口(静态资源处理)这也是典型适配器模式体现
     * c.
     *      ResourceHttpRequestHandler.setResourceResolvers 这是典型责任链模式体现
     */
    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }
}
