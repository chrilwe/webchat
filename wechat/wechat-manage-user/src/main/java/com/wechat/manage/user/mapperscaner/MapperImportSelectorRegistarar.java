package com.wechat.manage.user.mapperscaner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.wechat.manage.user.Mapper.UserMapper;

public class MapperImportSelectorRegistarar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder genericBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Mapper.class);
		GenericBeanDefinition bd = (GenericBeanDefinition)genericBeanDefinition.getBeanDefinition();
		//通过构造方法，告知spring应该传入哪一个接口进行jdk动态代理
		bd.getConstructorArgumentValues().addGenericArgumentValue(bd.getBeanClassName());
		bd.setBeanClass(MyFactoryBean.class);
		registry.registerBeanDefinition("mapper", bd);
	}

}
