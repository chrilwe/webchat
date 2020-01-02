package com.wechat.filesystem.common.aspect;

import org.springframework.stereotype.Component;

import com.wechat.filesystem.common.annotation.CheckImageFile;

@Component
public class Test {
	
	@CheckImageFile(index=0)
	public void test(){
		System.out.println("test----");
	}
}
