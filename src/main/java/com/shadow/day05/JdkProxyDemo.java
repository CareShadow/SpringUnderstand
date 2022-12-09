package com.shadow.day05;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName JdkProxyDemo
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/7 20:57
 * @Version 1.0
 **/
public class JdkProxyDemo {
    /**
     * Jdk代理:
     * 代理类是目标类的兄弟,他们两共同实现同一个接口, 对修饰符没有限制,但必须要实现接口
     * 代理类执行方法 --> 进入newProxyInstance中的增强方法InvocationHandler中 --> 通过反射实现目标类的方法
     */
    interface Foo {
        void foo();
    }

    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("foo");
        }
    }

    public static void main(String[] args) {
        Target target = new Target();
        ClassLoader loader = JdkProxyDemo.class.getClassLoader();
        Foo proxy = (Foo) Proxy.newProxyInstance(loader, new Class[]{Foo.class}, (p, method, args1) ->  {
            System.out.println("before");
            // 反射实现方法的调用
            Object result = method.invoke(target, args1);
            System.out.println("after");
            return result;
        });
        proxy.foo();
    }
}
