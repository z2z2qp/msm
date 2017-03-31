package com.will.utils.scure;

import com.will.utils.HexByteUtil;

import java.security.MessageDigest;


public class Md5Secure{
	/**
	 * 将plaintext转化为md5值
	 * @param plaintext
	 * @return
	 */
	public static String encode(String plaintext) {
		if (plaintext != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(plaintext.getBytes());
				String resultString = HexByteUtil.byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
