/**
 * 
 */
package com.will.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * HttpPost工具类
 * 
 * @author DongJun
 * 
 */
public class HttpPost {

	/**
	 * 以Post方式向web接口请求数据,编码为utf-8
	 * 
	 * 超时时间5s,编码UTF8
	 * 
	 * @param actionUrl
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String post(String actionUrl, Map<String, Object> params)
			throws IOException {
		return post(actionUrl, params,5 * 1000);
	}
	
	/**
	 * 以Post方式向web接口请求数据,编码为utf-8
	 * 
	 * 编码UTF8
	 * 
	 * @param actionUrl
	 * @param params
	 * @param timeout 超时时间  毫秒
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String post(String actionUrl, Map<String, Object> params,int timeout)
			throws IOException {
		if (actionUrl == null || actionUrl.equals(""))
			throw new IllegalArgumentException("actionUrl");
		if (params == null)
			throw new IllegalArgumentException("params");
		String sb2 = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		// try {
		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		// 设置超时
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes("UTF-8"));

		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();

		InputStream in = null;
		int res = conn.getResponseCode();
		if (res == 200) {
			in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				sb2 = sb2 + line;
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2;
	}
}
