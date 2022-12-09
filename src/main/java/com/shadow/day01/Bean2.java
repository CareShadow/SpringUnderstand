package com.shadow.day01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;

/**
 * @ClassName Bean2
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/4 14:57
 * @Version 1.0
 **/
@Slf4j
public class Bean2 implements DisposableBean {
    @PreDestroy
    public void destory1() {
        log.debug("销毁1");
    }

    @Override
    public void destroy() throws Exception {
        log.debug("销毁2");
    }

    public void destory3() {
        log.debug("销毁3");
    }
}
