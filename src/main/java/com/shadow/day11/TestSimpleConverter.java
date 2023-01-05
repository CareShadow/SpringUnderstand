package com.shadow.day11;

import org.springframework.beans.SimpleTypeConverter;

import java.util.Date;

/**
 * @ClassName TestSimpleConverter
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/5 19:35
 * @Version 1.0
 **/
public class TestSimpleConverter {
    public static void main(String[] args) {
        // 仅有类型转换的功能
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        Integer number = typeConverter.convertIfNecessary("13", int.class);
        Date date = typeConverter.convertIfNecessary("1999/03/04", Date.class);
        System.out.println(number);
        System.out.println(date);
    }
}
