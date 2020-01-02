package com.wechat.auth.cglib;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 产生cglib代理对象
 * @author chrilwe
 *
 */
public class CglibEnchancer {
	
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Service.class);
		enhancer.setCallback(new CglibMethodInterceptor());
		Service proxy = (Service) enhancer.create();
		String method1 = proxy.method1();
		System.out.println(method1);
	}
}
