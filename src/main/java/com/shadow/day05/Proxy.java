package com.shadow.day05;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @ClassName Proxy
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/11 10:47
 * @Version 1.0
 **/
public class Proxy extends CglibTarget{
    private MethodInterceptor interceptor;


    public void setInterceptor(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    static Method save;
    static Method save1;
    static Method save2;
    static MethodProxy saveMethodProxy;
    static MethodProxy save1MethodProxy;
    static MethodProxy save2MethodProxy;
    static {
        try {
            save = CglibTarget.class.getDeclaredMethod("save");
            save1 = CglibTarget.class.getMethod("save", int.class);
            save2 = CglibTarget.class.getMethod("save", long.class);
            saveMethodProxy  = MethodProxy.create(CglibTarget.class, Target.class, "()V", "save", "saveSuper");
            save1MethodProxy  = MethodProxy.create(CglibTarget.class, Target.class, "(I)V", "save1", "save1Super");
            save2MethodProxy  = MethodProxy.create(CglibTarget.class, Target.class, "(J)V", "save2", "save2Super");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }
    public void saveSuper() {
        super.save();
    }

    public void save1Super(int i) {
        super.save(i);
    }

    public void save2Super(long j) {
        super.save(j);
    }

    @Override
    public void save() {
        try {
            interceptor.intercept(this, save, new Object[0], saveMethodProxy);
        } catch (Throwable throwable) {
           throw new UndeclaredThrowableException(throwable);
        }
    }

    @Override
    public void save(int i) {
        try {
            interceptor.intercept(this, save1, new Object[]{i}, save1MethodProxy);
        } catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    @Override
    public void save(long j) {
        try {
            interceptor.intercept(this, save2, new Object[]{j}, save2MethodProxy);
        } catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }
}
