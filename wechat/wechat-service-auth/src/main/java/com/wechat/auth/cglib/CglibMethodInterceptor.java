package com.wechat.auth.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibMethodInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
		System.out.println("methodname----"+arg1.getName());
		//Object invoke = arg3.invokeSuper(arg0, arg2);
		return null;
	}

}
