package com.wechat.filesystem.test.hadoop.hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class TestHdfs {
	public static void main(String[] args) throws Exception {
		System.setProperty("hadoop.home.dir", "C://Users//Kim//Downloads//hadoop-3.2.1");
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.43.163:9000"), new Configuration());
		
		//从本地文件上传
		//fileSystem.copyFromLocalFile(new Path("C://Users//Kim//Desktop//password.txt"), new Path("/password.txt"));
		
		//小文件合并
		FSDataOutputStream outputStream = fileSystem.create(new Path("/mergeFile.txt"));
		File file = new File("C://Users//Kim//Desktop//merge");
		File[] listFiles = file.listFiles();
		for(File f : listFiles) {
			FileInputStream input = new FileInputStream(f);
			long copy = IOUtils.copy(input, outputStream);
			System.out.println(copy);
			input.close();
		}
		outputStream.close();
		
		//创建目录
		//fileSystem.mkdirs(new Path("/test"));
		
		//文件是否存在
		//boolean exists = fileSystem.exists(new Path("/password.txt"));
		//System.out.println(exists);
		
		
		//创建文件
		//fileSystem.create(new Path("/test1/text.txt"));
		
		
		//文件下载
		//FSDataInputStream output = fileSystem.open(new Path("/password.txt"));
	}
}
