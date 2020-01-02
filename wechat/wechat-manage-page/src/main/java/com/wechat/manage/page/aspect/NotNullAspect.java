package com.wechat.manage.page.aspect;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.wechat.base.common.msg.Msg;
import com.wechat.manage.page.annotation.NotNull;

/**
 * 
 * @author chrilwe
 *
 */
@Component
@Aspect
public class NotNullAspect implements Ordered {
	
	@Pointcut("@annotation(com.wechat.manage.page.annotation.NotNull)")
	public void pointCut(){}
	
	@Around("pointCut()")
	public void around(ProceedingJoinPoint joinpoint) {
		Object[] args = joinpoint.getArgs();
		if(args == null || args.length <= 0) {
			throw new RuntimeException(Msg.REQUEST_PARAM_ERR);
		}
		String currentMethodName = joinpoint.getSignature().getName();
		System.out.println("--------当前执行方法名称----------"+currentMethodName);
		
		Method targetMethod = null;
		Method[] methods = joinpoint.getTarget().getClass().getMethods();
		for(Method method : methods) {
			String methodName = method.getName();
			if(methodName.equals(currentMethodName)) {
				targetMethod = method;
				break;
			}
		}
		NotNull notNullAnnotation = targetMethod.getAnnotation(NotNull.class);
		int[] methodParamIndexs = notNullAnnotation.methodParamIndexs();
		Set<Integer> set = new HashSet<Integer>();
		for(int index : methodParamIndexs) {
			set.add(index);
		}
		
		for(int index : set) {
			Object object = args[index];
			if(object == null) {
				throw new RuntimeException(Msg.REQUEST_PARAM_ERR);
			}
		}
	}

	@Override
	public int getOrder() {
		return 100;
	}
}
