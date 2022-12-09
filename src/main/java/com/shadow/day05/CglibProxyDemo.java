package com.shadow.day05;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @ClassName CglibProxyDemo
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/7 22:36
 * @Version 1.0
 **/
public class CglibProxyDemo {

    /**
     * Cglib机制:
     * 代理类是目标类的子类, 通过继承重写目标类的方法进行代理增强,所以目标类和代理方法不能用final修饰符来修饰
     */
    static class Target {
        public void foo() {
            System.out.println("target foo");
        }
    }

    public static void main(String[] args) {
        Target target = new Target();

        Target proxy = (Target) Enhancer.create(Target.class, (MethodInterceptor) (p, method, args1, methodProxy) -> {
            System.out.println("before...");
//            Object result = method.invoke(target, args1); 使用了反射机制
            Object result = methodProxy.invoke(target, args1); // 未使用反射机制, 效率较高
//            methodProxy.invokeSuper(p, args1) 未用到目标对象, 也未使用反射机制
            System.out.println("after...");
            return result;
        });

        proxy.foo();
    }
}
