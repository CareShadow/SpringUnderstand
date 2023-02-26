package com.shadow.day20;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

/**
 * @ClassName Bean
 * @Description TODO
 * @Author CareShadow
 * @Date 2023/2/26 19:48
 * @Version 1.0
 **/
public class Bean {
    private BeanFactory beanFactory;

    private ObjectFactory<ApplicationContext> objectFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public ObjectFactory<ApplicationContext> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "beanFactory=" + beanFactory +
                '}';
    }
}
