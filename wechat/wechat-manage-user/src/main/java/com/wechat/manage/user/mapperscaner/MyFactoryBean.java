package com.wechat.manage.user.mapperscaner;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean<T> implements FactoryBean<T> {
	
	private Class<T> interfaceClass;

	public MyFactoryBean(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	@Override
	public T getObject() throws Exception {
		
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{interfaceClass}, new MapperInvocationHandler());
	}

	@Override
	public Class<?> getObjectType() {
		
		return interfaceClass;
	}

}
