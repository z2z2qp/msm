package com.will.utils.jsonparser;

import com.alibaba.fastjson.JSON;

public interface JSONParser<E> {
	E parse(String str) throws ParseErrorException;

	default Object parseObj(String str)throws ParseErrorException{
		Object obj = null;
		try {
			obj = JSON.parse(str);
			if (obj == null)
				throw new ParseErrorException("无法将数据转换成JSONObect或JSONArray:"
						+ str);
		} catch (Exception e) {
			throw new ParseErrorException(e.getMessage());
		}
		return obj;
	}
}
