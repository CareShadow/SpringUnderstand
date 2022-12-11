package com.shadow.day05;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName Test
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/11 11:06
 * @Version 1.0
 **/
public class Test {
    /**
     * JDK和Cglib的代理区别
     * JDK要16次后,才给没有方法生成一个代理类,来进行方法的调用,不再使用反射调用方法
     * Cglib在MethodProxy创建的时候就会生成TargetFastProxy,ProxyFastProxy来代理目标类的方法和代理类的原始方法
     * 总结: Cglib的代理类数量要比JDK的少一些,更快捷
     */
    public static void main(String[] args) {
        CglibTarget target = new CglibTarget();

        Proxy proxy = new Proxy();
        proxy.setInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before....");
//                Object invoke = method.invoke(target, objects);
                Object invoke = methodProxy.invoke(target, objects);// 未使用反射
//                methodProxy.invokeSuper(o, objects) // 未使用目标对象
                return invoke;
            }
        });
        proxy.save();
    }
}
