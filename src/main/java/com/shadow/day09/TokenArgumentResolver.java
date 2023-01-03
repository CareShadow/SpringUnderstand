package com.shadow.day09;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

/**
 * @ClassName TokenArgumentResolver
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/3 21:34
 * @Version 1.0
 **/
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    // 看该方法上是否有此注解
    public boolean supportsParameter(MethodParameter parameter) {
        // 把方法参数封装成一个对象了
        Annotation annotation = parameter.getParameterAnnotation(Token.class);
        return annotation != null;
    }

    @Override
    // 怎么处理这个参数
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
       // webRequest是一个把request和response封装的对象
        String token = webRequest.getHeader("token");
        return token;
    }
}
