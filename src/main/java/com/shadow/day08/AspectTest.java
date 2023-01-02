package com.shadow.day08;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName AspectTest
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/14 21:19
 * @Version 1.0
 **/
public class AspectTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect",Aspect1.class);
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        // 切面匹配创造器
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        context.refresh(); // 启动context容器
//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
        AnnotationAwareAspectJAutoProxyCreator creator = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        Method method = AbstractAdvisorAutoProxyCreator.class.getDeclaredMethod("findEligibleAdvisors", Class.class, String.class);
        method.setAccessible(true);
        List<Advisor> advisors = (List<Advisor>) method.invoke(creator, Aspect1.class, "aspect1");
        for (Advisor advisor : advisors) {
            System.out.println(advisor);
        }
    }
    static class Target1 {
        public void foo() {
            System.out.println("Target1 foo");
        }
        public void bar() {
            System.out.println("Target1 bar");
        }
    }
    static class Aspect1 {
        @Before("execution(* foo())")
        public void before() {
            System.out.println("aspect1 before...");
        }
        @After("execution(* foo())")
        public void after() {
            System.out.println("aspect1 after...");
        }
    }

    static class Config {
        @Bean
        public Advisor advisor3(MethodInterceptor advice3) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut, advice3);
        }

        @Bean
        public MethodInterceptor advice3() {
            return new MethodInterceptor() {
                @Override
                public Object invoke(MethodInvocation invocation) throws Throwable {
                    System.out.println("advice3 before");
                    Object proceed = invocation.proceed();
                    System.out.println("advice3 after");
                    return proceed;
                }
            };
        }
    }
}
