package com.wechat.manage.user.aspect;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.wechat.manage.user.aspect")
@Import(MyImportSelector.class)
public class Config {

}
