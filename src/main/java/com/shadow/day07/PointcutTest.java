package com.shadow.day07;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @ClassName PointcutTest
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/13 22:15
 * @Version 1.0
 **/
public class PointcutTest {
    public static void main(String[] args) throws NoSuchMethodException {
        /**
         * a. 底层切点实现是如何匹配的：调用aspectj的匹配方法matches等
         * b. springAop提供注解 比较底层的是他实现了MethodMatcher接口，用来执行方法的匹配 比如：StaticMethodMatcherPointcut
         */
        AspectJExpressionPointcut pt1 = new AspectJExpressionPointcut();
        // 通过execution表达式来进行切点的查找
        pt1.setExpression("execution(* bar())");
        System.out.println(pt1.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pt1.matches(T1.class.getMethod("bar"), T1.class));
        // 通过注解实现切点的查找
        AspectJExpressionPointcut pt2 = new AspectJExpressionPointcut();
        pt2.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
        System.out.println(pt2.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pt2.matches(T1.class.getMethod("bar"), T1.class));
        // 通过类上的注解实现切点的查找
        StaticMethodMatcherPointcut pt3 = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                // 检查方法上是否加上了Transactional注解
                MergedAnnotations annotations = MergedAnnotations.from(method);
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }
                // 查看类上是否加上了Transactional注解
                // MergedAnnotations.SearchStrategy.TYPE_HIERARCHY继承树上有这个注解都会返回true
                annotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if(annotations.isPresent(Transactional.class)) {
                    return true;
                }
                return false;
            }
        };
        System.out.println(pt3.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pt3.matches(T1.class.getMethod("bar"), T1.class));
        System.out.println(pt3.matches(T2.class.getMethod("foo"), T2.class));
        System.out.println(pt3.matches(T3.class.getMethod("foo"), T3.class));
    }

    static class T1 {
        @Transactional
        public void foo() {
            System.out.println("T1 foo");
        }

        public void bar() {
            System.out.println("T1 bar");
        }
    }

    @Transactional
    static class T2 {
        public void foo() {
            System.out.println("T2 foo");
        }

        public void bar() {
            System.out.println("T2 bar");
        }
    }

    @Transactional
    interface I3 {
        void foo();
    }

    static class T3 implements I3 {
        @Override
        public void foo() {

        }
    }
}
