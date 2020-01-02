package com.wechat.manage.user.aspect;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.MethodProxy;

public class MethodInterceptor implements org.springframework.cglib.proxy.MethodInterceptor {

	@Override
	public Object intercept(Object proxyObject, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		
		return null;
	}
	
}
