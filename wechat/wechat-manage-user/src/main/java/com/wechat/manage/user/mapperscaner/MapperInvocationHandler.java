package com.wechat.manage.user.mapperscaner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.wechat.manage.user.mapperscaner.annotation.MySelect;

public class MapperInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MySelect myselect = method.getAnnotation(MySelect.class);
		String selectSql = myselect.selectSql();
		System.out.println(selectSql);
		return "query";
	}

}
