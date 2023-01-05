package com.shadow.day11;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName MySimpleDateFormat
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/5 21:55
 * @Version 1.0
 **/
@Slf4j
public class MySimpleDateFormat implements Formatter<Date> {
    private String desc;

    public MySimpleDateFormat(String desc) {
        this.desc = desc;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        log.debug(">>>>> 进入了: {}", desc);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy|MM|dd");
        return sdf.parse(text);
    }

    @Override
    public String print(Date date, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy|MM|dd");
        return sdf.format(date);
    }
}
