package com.shadow.day22.factorybean;

import com.shadow.day21.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName FactoryBeanDemo
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/3/1 22:40
 * @Version 1.0
 **/
public class FactoryBeanDemo implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setName("liangguanzhong");
        user.setAge(22);
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
