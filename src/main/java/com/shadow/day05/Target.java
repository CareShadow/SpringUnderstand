package com.shadow.day05;

import java.lang.reflect.Method;

/**
 * @ClassName Target
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/10 14:29
 * @Version 1.0
 **/

interface  Foo {
    void foo();
    int bar();
}

/**
 * JDK代理：实现同一接口,
 * InvocationHandler接口,是让用户自定义增强内容,
 * proxy.foo() -> foo()判断调用的什么方法,将参数传入InvocationHandler中的invoke方法中
 * -> invoke方法执行(目标类方法,增强内容)
 * 接口方法的返回值, 异常的抛出情况
 */
interface InvocationHandler {
    Object invoke($Proxy0 proxy, Method method, Object[] args) throws Throwable;
}
public class Target implements Foo{
    @Override
    public void foo() {
        System.out.println("target foo");
    }

    @Override
    public int bar() {
        System.out.println("target bar");
        return 1;
    }

    public static void main(String[] args) {
        $Proxy0 proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke($Proxy0 proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before....");
                Object invoke = method.invoke(new Target(), args);
                return invoke;
            }
        });
        proxy.foo();
        proxy.bar();
    }
}
