package com.will.utils.jsonparser;
public class ParseErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5591039112078969830L;

	public ParseErrorException() {
		super("解析出错");
	}

	public ParseErrorException(String reason) {
		super("解析出错:" + reason);
	}
}
