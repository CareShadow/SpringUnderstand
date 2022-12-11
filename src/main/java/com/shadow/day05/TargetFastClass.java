package com.shadow.day05;

import org.springframework.cglib.core.Signature;

/**
 * @ClassName TargetFastClass
 * @Description TODO
 * @Author 18451
 * @Date 2022/12/11 14:08
 * @Version 1.0
 **/
public class TargetFastClass {
    static Signature s0 = new Signature("save", "()V");
    static Signature s1 = new Signature("save", "(I)V");
    static Signature s2 = new Signature("save", "(J)V");

    public int getIndex(Signature signature) {
        if (s0.equals(signature)) {
            return 0;
        } else if (s1.equals(signature)) {
            return 1;
        } else if (s2.equals(signature)) {
            return 2;
        }
        return -1; //代表没找到
    }

    // 通过父类方法直接调用,不经过反射
    public Object invoke(int index, Object target, Object[] args) {
        if (index == 0) {
            ((CglibTarget) target).save();
        } else if (index == 1) {
            ((CglibTarget) target).save(((int) args[0]));
        } else if (index == 2) {
            ((CglibTarget) target).save(((long) args[0]));
        } else {
            throw new RuntimeException("无此类型的方法");
        }
        return null;
    }
}
