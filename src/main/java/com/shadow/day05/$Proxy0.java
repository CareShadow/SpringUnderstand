package com.shadow.day05;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @ClassName $Proxy0
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/10 14:31
 * @Version 1.0
 **/
public class $Proxy0 implements Foo{
    private InvocationHandler p;

    public $Proxy0(InvocationHandler p) {
        this.p = p;
    }

    @Override
    public void foo() {
        try {
            p.invoke(this, foo, new Object[0]);
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public int bar() {
        try {
            Object invoke = p.invoke(this, bar, new Object[0]);
            return (int) invoke;
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }
    // 性能问题: 每次使用方法时,都会进行方法的反射获取
    static Method foo;
    static Method bar;
    static {
        try {
            foo = Foo.class.getDeclaredMethod("foo");
            bar = Foo.class.getDeclaredMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }
}
