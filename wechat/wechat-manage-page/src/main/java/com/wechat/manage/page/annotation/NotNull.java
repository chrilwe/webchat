package com.wechat.manage.page.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不允许指定的参数为空
 * @author chrilwe
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
	public int[] methodParamIndexs();//指定不能为空的方法的参数位置
}
