package com.wechat.manage.user.aspect;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

@Component
public class AopBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equals(Service.class.getName())) {
			System.out.println("----post service----");
			Enhancer enhancer = new Enhancer();
			
		}
		return bean;
	}

}
