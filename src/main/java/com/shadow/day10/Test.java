package com.shadow.day10;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @ClassName Test
 * @Description TODO
 * @Author 18451
 * @Date 2023/1/4 22:26
 * @Version 1.0
 **/
public class Test {
    // 编译Java文件是取不到参数名称的,要通过 -parameter -g(只适用普通类,接口不可以)
    // 来编译Java文件,两种方式的存储参数名字的地方不一样 一个是参数表,一个是本地变量表, 反射取不到本地变量表中的名字
    // spring中的LocalVariableTableParameterNameDiscoverer可以取到本地方法表中的名字
    public static void main(String[] args) throws NoSuchMethodException {
        Method foo = Bean1.class.getMethod("foo", String.class, int.class);
        for (Parameter parameter : foo.getParameters()) {
            System.out.println(parameter.getName());
        }
        Method interfaceFoo = Bean2.class.getMethod("foo", String.class, int.class);
        for (Parameter parameter : interfaceFoo.getParameters()) {
            System.out.println(parameter.getName());
        }
    }
}
