package com.wechat.manage.user.mapperscaner;

import com.wechat.manage.user.mapperscaner.annotation.MySelect;

public interface Mapper {
	
	@MySelect(selectSql="select * from tb")
	public void query();
}
