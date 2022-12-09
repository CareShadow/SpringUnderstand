package com.shadow.day03;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @ClassName F1
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/5 21:45
 * @Version 1.0
 **/
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class F1 {

}
