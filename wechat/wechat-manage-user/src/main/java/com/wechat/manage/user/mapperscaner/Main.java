package com.wechat.manage.user.mapperscaner;

import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.wechat.manage.user.Mapper.UserMapper;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext acc = new AnnotationConfigApplicationContext(App.class);
		Service service = acc.getBean(Service.class);
		service.select();
		
	}
}
