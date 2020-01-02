package com.wechat.im.server.im_server.handler;

import com.wechat.im.server.service.AuthService;

/**
 * 定义认证方式，根据传入的类的class通过反射创建认证类
 * @author chrilwe
 *
 */
public class AuthServiceBuilder {
	
	private static class Singleton {
		private static AuthServiceBuilder bulider = null;
		static {
			bulider = new AuthServiceBuilder();
		}
		
		private static AuthServiceBuilder getInstance() {
			return bulider;
		}
	}
	
	public static AuthServiceBuilder getInstance() {
		return Singleton.getInstance();
	}
	
	public AuthService build(Class<? extends AuthService> type) throws InstantiationException, IllegalAccessException {
		AuthService authService = type.newInstance();
		return authService;
	}
}
