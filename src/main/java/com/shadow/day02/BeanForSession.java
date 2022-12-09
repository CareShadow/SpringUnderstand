package com.shadow.day02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @ClassName BeanForSession
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/4 21:40
 * @Version 1.0
 **/
@Slf4j
@Scope("session")
@Component
public class BeanForSession {

    @PreDestroy
    public void destroy() {
        log.debug("destroy");
    }
}
