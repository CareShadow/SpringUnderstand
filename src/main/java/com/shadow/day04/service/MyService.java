package com.shadow.day04.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName MyService
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/5 22:40
 * @Version 1.0
 **/
@Service
@Slf4j
public class MyService {
    public MyService() {

    }
    public void foo() {
        log.debug("foo()");
    }
}
