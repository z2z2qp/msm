package com.will.utils;

import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * The type PropertyLoader.
 *
 * @Auth Will
 * @Date 2016 -12-13 16:04:53
 */
public class PropertyLoader {
	private static Logger log = LogUtils.getLogger(PropertyLoader.class);

	/**
	 * 根据Reader读取Properties
	 *
	 * @param reader the reader
	 * @return the properties
	 */
	public static Properties getProperties(Reader reader) {
		Properties properties;
		properties = new Properties();
		try {
			properties.load(reader);
			return properties;
		} catch (Exception e) {
			log.debug("属性文件解析失败" + e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据文件绝对路径字符集编码读取
	 *
	 * @param filepath    the filepath
	 * @param charsetName the charset name
	 * @return the properties from absolute path
	 */
	public static Properties getPropertiesFromAbsolutePath(String filepath,
			String charsetName) {
		try {
			Reader reader = new InputStreamReader(
					new FileInputStream(filepath), charsetName);
			return getProperties(reader);
		} catch (Exception e) {
			log.debug("属性文件(" + filepath + ")加载失败" + e.getMessage());
			return null;
		}
	}

	/**
	 * 根据绝对文件路径默认utf-8编码读取
	 *
	 * @param filepath the filepath
	 * @return the properties
	 * @Auth Will
	 * @Date 2016 -12-13 16:08:23
	 */
	public static Properties getPropertiesFromAbsolutePath(String filepath){
		return getPropertiesFromAbsolutePath(filepath,"UTF-8");
	}
	/**
	 * 根据文件相对项目路径符集编码读取
	 *
	 * @param filepath    the filepath
	 * @param charsetName the charset name
	 * @return the properties from class path
	 */
	public static Properties getPropertiesFromClassPath(String filepath,
			String charsetName) {
		try {
			Reader reader = new InputStreamReader(PropertyLoader.class
					.getClassLoader().getResourceAsStream(filepath), charsetName);
			return getProperties(reader);
		} catch (Exception e) {
			log.debug("属性文件(" + filepath + ")加载失败" + e.getMessage());
			return null;
		}
	}

	/**
	 * 根据文件相对项目路径默认utf-8编码读取
	 *
	 * @param filepath the filepath
	 * @return the properties
	 * @Auth Will
	 * @Date 2016 -12-13 16:08:23
	 */
	public static Properties getPropertiesFromClassPath(String filepath){
		return getPropertiesFromClassPath(filepath,"UTF-8");
	}
}
