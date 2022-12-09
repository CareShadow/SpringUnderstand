package com.shadow.day04.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName MyAspect
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/5 22:35
 * @Version 1.0
 **/
@Aspect
public class MyAspect {
    private static final Logger log = LoggerFactory.getLogger(MyAspect.class);
    @Before("execution(* com.shadow.day04.service.MyService.foo())")
    public void before() {
        log.debug("before()");
    }
}
