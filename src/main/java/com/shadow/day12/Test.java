package com.shadow.day12;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @ClassName Test
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/7 21:32
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        // 目的: 取到泛型父类中的泛型参数的类型,常用于框架的设计
        // 1. jdk
        Type type = TeacherDao.class.getGenericSuperclass();
        System.out.println(type);
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            System.out.println(parameterizedType.getActualTypeArguments()[0]);
        }
        // 2. spring api

        // 默认一个参数, 若有多个使用resolveTypeArguments()
        Class<?> t = GenericTypeResolver.resolveTypeArgument(TeacherDao.class, Dao.class);
        System.out.println(t);
    }
}
