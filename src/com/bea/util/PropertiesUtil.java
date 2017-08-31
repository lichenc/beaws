package com.bea.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PropertiesUtil {

	/*
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	/*
	 * 获取本地配置文件
	 */
	private static final String debugFileName = "beabank.properties";
	
	
	
	//单例模式
	private static PropertiesUtil instance = null;
	public static Properties prop;
	/*
	 * 初始化
	 */
	public PropertiesUtil() {
		prop = new Properties();
		try {
			//String serverName = System.getenv("server_name");

			//System.out.println("*****PropertiesUtil.serverName:" + serverName);
			
			prop.load(getPropertiesFileFromClassPath(debugFileName));
			
		} catch(Exception e) {
			System.out.println("PropertiesUtil Exception");
			logger.error("Exception",e);
		}
				
	}	
	public static PropertiesUtil getInstance()
	{
		instance = new PropertiesUtil();
		return instance;
	}
	
	//
	public InputStream getPropertiesFileFromClassPath(String fileName) 
			throws FileNotFoundException 
	{
		InputStream inputStream = this.getClass().
								 getClassLoader().getResourceAsStream(fileName);
		if(inputStream == null)
		{
			throw new FileNotFoundException("Property file "+fileName+" not found in the ClassPath!");
		}
		
		return inputStream;
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getInstance().prop.getProperty("bea.uim.login.auth"));
	}
}
