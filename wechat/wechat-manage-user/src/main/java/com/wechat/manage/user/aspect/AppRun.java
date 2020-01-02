package com.wechat.manage.user.aspect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppRun {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		Service bean = context.getBean(Service.class);
		bean.method1();
	}
}
