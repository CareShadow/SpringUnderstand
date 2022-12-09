package com.shadow.day01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @ClassName Bean1
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/4 14:58
 * @Version 1.0
 **/
@Slf4j
public class Bean1 implements InitializingBean {
    @PostConstruct
    public void init1() {
        log.debug("初始化1");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("初始化2");
    }

    public void init3() {
        log.debug("初始化3");
    }
}
