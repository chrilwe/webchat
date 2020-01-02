package com.wechat.base.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

/**
 * add or remove cookie
 * @author chrilwe
 *
 */
public class CookieUtil {
	
	
	/**
	 * add cookie
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge, String domain) {
		Cookie cookie = new Cookie(cookieName,cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}
	
	/**
	 * 删除cookie
	 */
	public static void delete(String cookieName, HttpServletResponse response, HttpServletRequest request) {
		Cookie cookie = getCookieValueByName(request,cookieName);
		if(cookie == null) {
			return;
		}
		cookie.setValue("");
		response.addCookie(cookie);
	}
	
	public static Cookie getCookieValueByName(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			if(name.equals(cookieName)) {
				return cookie;
			}
		}
		return null;
	}
}
