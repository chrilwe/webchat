package com.wechat.auth.cglib;
/**
 * cglib被代理对象
 * @author chrilwe
 *
 */
public class Service {
	
	public String method1() {
		System.out.println("------执行method1--------");
		return "hello1";
	}
	
	public void method2() {
		System.out.println("------执行method2--------");
	}
}
