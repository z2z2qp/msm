package com.will.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}
}
