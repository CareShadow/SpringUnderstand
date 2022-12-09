package com.shadow.day03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @ClassName E
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/5 21:45
 * @Version 1.0
 **/
@Component
public class E {

    @Autowired
    private F1 f1;

    public String getF1() {
        return f1.toString();
    }
}
