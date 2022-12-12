package com.shadow.day06;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;


/**
 * @ClassName AdviceTest
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/12 22:41
 * @Version 1.0
 **/
public class AdviceTest {

    public static void main(String[] args) {
        // 1. 创建好切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())");

        // 2. 备好通知(前置通知,后置通知)
        MethodInterceptor advise = new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("before....");
                Object result = invocation.proceed();
                System.out.println("after....");
                return result;
            }
        };

        // 切面由切点和通知组成,一种定义
        // 3. 创建切面
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advise);

        // 4. 创建代理
        CglibTarget target = new CglibTarget();
        ProxyFactory proxyFactory = new ProxyFactory();
        // 目标对象
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);
//        proxyFactory.setInterfaces(I1.class);
        proxyFactory.setProxyTargetClass(false);
        // 代理对象, 代理对象是目标类的子类,代理对象和目标类实现同一个接口
        CglibTarget proxy = (CglibTarget) proxyFactory.getProxy();
        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();
        /**
         * 对于Spring AOP使用JDK还是Cglib的理解
         * proxyFactory中的ProxyTargetClass属性有关系
         * 1. proxyTargetClass = false 目标类是实现接口, JDK实现
         * 2. proxyTargetClass = false 目标类未实现接口, Cglib实现
         * 3. proxyTargetClass = true 总是Cglib实现
         * 重点: ProxyFactory无法知道目标类是否实现了接口,要通过设置setInterface来查看
         */
    }

    interface I1 {
        void foo();
        void bar();
    }

    static class Target implements I1 {
        @Override
        public void foo() {
            System.out.println("Target foo");
        }

        @Override
        public void bar() {
            System.out.println("Target bar");
        }
    }

    static class CglibTarget {
        void foo() {
            System.out.println("CglibTarget foo");
        }
        void bar() {
            System.out.println("CglibTarget bar");
        }
    }
}
