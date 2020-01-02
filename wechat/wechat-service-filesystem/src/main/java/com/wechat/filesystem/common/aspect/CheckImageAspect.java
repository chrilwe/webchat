package com.wechat.filesystem.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.wechat.filesystem.common.annotation.CheckImageFile;
import com.wechat.filesystem.common.model.FdfsProperties;
import com.wechat.filesystem.common.msg.FileMsg;

@Component
@Aspect
public class CheckImageAspect implements Ordered {
	
	@Autowired
	private FdfsProperties fdfsProperties;
	
	@Pointcut("@annotation(com.wechat.filesystem.common.annotation.CheckImageFile)")
	public void pointcut(){}
	
	@Around("pointcut()")
	public Object checkImage(ProceedingJoinPoint joinpoint) throws Throwable {
		Object[] args = joinpoint.getArgs();
		String methodName = joinpoint.getSignature().getName();
		System.out.println("methodname----"+methodName);
		Method[] methods = joinpoint.getTarget().getClass().getMethods();
		int index = 0;
		for (Method method : methods) {
			String name = method.getName();
			if(methodName.equals(name)) {
				CheckImageFile annotation = method.getAnnotation(CheckImageFile.class);
				index = annotation.index();
				break;
			}
		}
		MultipartFile image = (MultipartFile) args[index];
		this.validateImage(image);
		Object proceed = joinpoint.proceed();
		return proceed;
	}
	
	private void validateImage(MultipartFile image) {
		// check size
		long size = image.getSize();
		if(size > fdfsProperties.getImage_max()) {
			throw new RuntimeException(FileMsg.IMAGE_SIZE_OUT_INDEX);
		}
		
		// check type
		String originalFilename = image.getOriginalFilename();
		String[] split = originalFilename.split(".");
		if(split.length <= 1) {
			throw new RuntimeException(FileMsg.IMAGE_TYPE_UNSUPPORT);
		}
		int indexOf = originalFilename.indexOf(".");
		String fileExt = originalFilename.substring(indexOf + 1);
		if(!fdfsProperties.getImage_types().contains(fileExt)) {
			throw new RuntimeException(FileMsg.IMAGE_TYPE_UNSUPPORT);
		}
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 100;
	}

}
