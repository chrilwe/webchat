package com.wechat.manage.user.mapperscaner;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {
	
	@Autowired
	private Mapper mapper;
	
	public void select() {
		mapper.query();
	}

}
