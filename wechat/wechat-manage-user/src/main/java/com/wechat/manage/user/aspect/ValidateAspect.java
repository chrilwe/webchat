package com.wechat.manage.user.aspect;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.wechat.base.common.msg.Msg;
import com.wechat.manage.user.annotation.Validate;
import com.wechat.manage.user.common.msg.UserMsg;

@Component
@Aspect
public class ValidateAspect {
	
	@Pointcut("@annotation(com.wechat.manage.user.annotation.Validate)")
	public void pointCut() {}
	
	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinpoint) throws Throwable {
		Object[] args = joinpoint.getArgs();
		if(args == null) {
			throw new RuntimeException(Msg.REQUEST_PARAM_ERR);
		}
		if(args.length > 0) {
			String methodName = joinpoint.getSignature().getName();
			Method[] methods = joinpoint.getTarget().getClass().getMethods();
			
			Method targetMethod = null;
			for(Method method : methods) {
				String name = method.getName();
				if(name.equals(methodName)) {
					targetMethod = method;
					break;
				}
			}
			
			Validate validate = targetMethod.getAnnotation(Validate.class);
			int[] validateArgs = validate.validateArgs();
			String[] validateTypes = validate.validateType();
			if(validateArgs.length <= 0 || validateTypes.length <= 0 || validateArgs.length != validateTypes.length) {
				throw new RuntimeException(UserMsg.VALIDATE_ERR);
			}
			
			Set<Integer> argsSet = new HashSet<>();
			Set<String> validateTypesSet = new HashSet<String>();
			for(int arg : validateArgs) {
				argsSet.add(arg);
			}
			for(String arg : validateTypesSet) {
				validateTypesSet.add(arg);
			}
			
			this.processValidate(argsSet, validateTypesSet);
		}
		
		Object proceed = joinpoint.proceed();
		
		return proceed;
	}
	
	private void processValidate(Set<Integer> args, Set<String> args1) {
		String[] array = (String[]) args1.toArray();
		Integer[] array1 = (Integer[]) args.toArray();
		for(int i = 0;i < array.length;i++) {
			String validateType = array[i];
			Integer validateArgsIndex = array1[i];
			if(ValidateType.EMAIL.equals(validateType)) {
				this.validateEmail();
			} else if(ValidateType.PHONE.equals(validateType)) {
				this.validatePhone();
			} else {
				throw new RuntimeException("please check validateTpe is email or phone");
			}
		}
	}
	
	private void validateEmail() {
		System.out.println("校验邮箱格式是否正确");
	}
	
	private void validatePhone() {
		System.out.println("校验手机号码是否正确");
		
	}
}
