package com.shadow.day22.factory;

import com.shadow.day21.User;

/**
 * @InterfaceName UserFactory
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/3/1 22:22
 * @Version 1.0
 **/
public interface UserFactory {
    default User createUser() {
        User user = new User();
        user.setName("lxl");
        user.setAge(20);
        return user;
    }
}
