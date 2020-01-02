package com.wechat.manage.user.mapperscaner;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.wechat.manage.user.mapperscaner")
@Import(MapperImportSelectorRegistarar.class)
public class App {

}
