package com.wechat.filesystem.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.MultipartConfigElement;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.wechat.filesystem.common.model.FdfsProperties;
import com.wechat.filesystem.common.model.HadoopProperties;

@Configuration
public class AppConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@ConfigurationProperties(prefix = "fdfs")
	public FdfsProperties fdfsProperties() {

		return new FdfsProperties();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "hadoop")
	public HadoopProperties hadoopProperties() {
		return new HadoopProperties();
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 文件最大
		factory.setMaxFileSize("10240MB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("102400MB");
		return factory.createMultipartConfig();
	}
	
	@Autowired
	private HadoopProperties hadoopProperties;
	
	@Bean
	public FileSystem fileSystem() throws IOException, URISyntaxException {
		System.setProperty("hadoop.home.dir", hadoopProperties.getHadoop_home_dir());
		FileSystem fileSystem = FileSystem.get(new URI(hadoopProperties.getHdfsURI()), new org.apache.hadoop.conf.Configuration());
		return fileSystem;
	}
}
