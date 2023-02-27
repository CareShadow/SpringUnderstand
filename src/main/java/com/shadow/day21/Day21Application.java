package com.shadow.day21;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @ClassName Day21Application
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/2/27 22:08
 * @Version 1.0
 **/
public class Day21Application {
    public static void main(String[] args) {
        // BeanDefinitionBuilder 进行生成BeanDefinition
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        builder.addPropertyValue("age", 18).addPropertyValue("name", "lxl");
        // 获取BeanDefinition
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        System.out.println(beanDefinition);

        // 通过AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(User.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("age", 18);
        propertyValues.addPropertyValue("name", "lxl");

        genericBeanDefinition.setPropertyValues(propertyValues);
    }
}
