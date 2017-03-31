/**
 * @Title BaiduUtil.java
 * @Package com.ltmonitor.util
 * @Description
 * @Author 庄佳彬
 * @Date 2015年12月11日
 * @Version v1.0
 */
package com.will.utils.map.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.will.utils.CollectionUtils;
import com.will.utils.PropertyLoader;
import com.will.utils.map.GeoPoint;
import com.will.utils.map.MapUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;


/**
 * <li>ClassName: BaiduUtil</li>
 * <li>Description:</li> 
 * <li>Change:</li>
 *  <li>Date: 2015年12月11日 下午1:07:32</li>
 * @author 庄佳彬
 */
public class BaiduUtil extends MapUtil {

	private static Logger log = Logger.getLogger(BaiduUtil.class);
	private static String SK ;//= "zMkeFDU6gTnkAkWMMiw8nElvYhYIGOt4";
	private static String AK ;//= "APOCRdC8SRKgMTb5oPFqr88I";
	private static boolean isInit = false;

	static{
		init();
	}

	
	private static BaiduUtil bu = null;//单例模式
	
	private BaiduUtil(){
		if(!isInit){
			init();
		}
	}

	private static void init(){
		try {
			Properties properties =  PropertyLoader.getPropertiesFromClassPath("baidu.properties");
			SK = properties.getProperty("SK");
			AK = properties.getProperty("AK");
			isInit = true;
		} catch (Exception e) {
			log.error("other Exception",e);
		}
	}
	
	public static BaiduUtil getBaiduUtil(){
		if(bu == null){
			bu = new BaiduUtil();
		}
		return bu;
	}

	/**
	 * 
	 * <p>Title: location2Adress</p>
	 * <p>Description:坐标转地址</p> 
	 * @author 庄佳彬
	 * @param pointLatLng
	 * @return String
	 */
	public String location2Adress(GeoPoint pointLatLng) {
		try {
			Map<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("location", pointLatLng.getLan()+","+pointLatLng.getLon());
			paramsMap.put("output", "json");
			paramsMap.put("ak", AK);
			String queryStr = toQueryString(paramsMap);
			String wholeStr = new String("/geocoder/v2/?" + queryStr + SK);
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			String SN = MD5(tempStr);
			paramsMap.put("sn", SN);

			URL url = new URL("http://api.map.baidu.com/geocoder/v2/?" + toQueryString(paramsMap));
			URLConnection connection = url.openConnection();

			InputStream in = connection.getInputStream();
			String resultJson = null;
			byte[] tt = new byte[in.available()];
			while (in.read(tt, 0, tt.length) != -1) {
				resultJson = new String(tt, "utf-8");
			}
			return getFormattedAddress(resultJson);
		} catch (MalformedURLException e) {
			log.error("坐标转地址异常", e);
		} catch (IOException e) {
			log.error("坐标转地址异常", e);
		}catch (Exception e) {
			log.error("坐标转地址异常", e);
		}
		return null;
	}
//  不支持sn验证
//	/**
//	 * 
//	 * <p>Title: getMarkersStaticimage</p>
//	 * <p>Description:</p> 
//	 * @author 庄佳彬
//	 * @Date 2015年12月25日
//	 * @param ps
//	 * @param pe
//	 * @return String
//	 */
//	public static String getMarkersStaticimage(PointLatLng ps,PointLatLng pe){
//		String staticimagePath = null;
//		try {
//			Map<String, String> paramsMap = new LinkedHashMap<String, String>();
//			paramsMap.put("ak", AK);
//			paramsMap.put("width", "400");
//			paramsMap.put("height", "200");
//			paramsMap.put("center", "北京");
//			paramsMap.put("markers", "116.34,39.90|116.403874,39.914888");
//			paramsMap.put("zoom", "10");
//			paramsMap.put("markerStyles", "s,A,0xff0000");
//			String queryStr = toQueryString(paramsMap);
//			String wholeStr = new String("/staticimage/v2?" + queryStr + SK);
//			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
//			String SN = MD5(tempStr);
//			paramsMap.put("sn", SN);
//			
//			
//			
//			URL url = new URL("http://api.map.baidu.com/staticimage/v2?" + toQueryString(paramsMap));
//			URLConnection connection = url.openConnection();
//
//			InputStream in = connection.getInputStream();
//			String resultJson = null;
//			byte[] tt = new byte[in.available()];
//			while (in.read(tt, 0, tt.length) != -1) {
//				resultJson = new String(tt, "utf-8");
//			}
//			System.out.println(resultJson);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return staticimagePath;
//	}
	
	
	
	/**
	 * 
	 * <p>Title: geoconvPointLatLng</p>
	 * <p>Description：坐标转换</p> 
	 * @author 庄佳彬
	 * @param ps
	 * @return List<PointLatLng>
	 */
	public List<GeoPoint> geoconvPointLatLng(List<GeoPoint> ps){
		try {
			StringBuffer sb = new StringBuffer();
			Map<String, String> paramsMap = new LinkedHashMap<String, String>();
			for (GeoPoint pointLatLng : ps) {
				sb.append(pointLatLng.getLon()).append(",").append(pointLatLng.getLan());
				sb.append(";");
			}
			paramsMap.put("coords", sb.substring(0, sb.length() - 1));
			paramsMap.put("output", "json");
			paramsMap.put("ak", AK);
			paramsMap.put("from", "1");
			paramsMap.put("to", "5");
			String queryStr = toQueryString(paramsMap);
			String wholeStr = new String("/geoconv/v1/?" + queryStr + SK);
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			String SN = MD5(tempStr);
			paramsMap.put("sn", SN);
			
			URL url = new URL("http://api.map.baidu.com/geoconv/v1/?" + toQueryString(paramsMap));
			URLConnection connection = url.openConnection();

			InputStream in = connection.getInputStream();
			String resultJson = null;
			byte[] tt = new byte[in.available()];
			while (in.read(tt, 0, tt.length) != -1) {
				resultJson = new String(tt, "utf-8");
			}
			return getPointLatLng(resultJson);
		} catch (UnsupportedEncodingException e) {
			log.error("坐标转换异常", e);
		} catch (MalformedURLException e) {
			log.error("坐标转换异常", e);
		} catch (IOException e) {
			log.error("坐标转换异常", e);
		}
		return null;
	}

	/**
	 * 
	 * <p>Title: geoconvPointLatLng</p>
	 * <p>Description:单坐标转换</p> 
	 * @author 庄佳彬
	 * @param p
	 * @return PointLatLng
	 */
	public GeoPoint geoconvPointLatLng(GeoPoint p){
		List<GeoPoint> list = new ArrayList<GeoPoint>();
		list.add(p);
		List<GeoPoint> result = geoconvPointLatLng(list);
		if(result != null && result.size() > 0){
			return result.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 
	 * <p>Title: getFormattedAddress</p>
	 * <p>Description:解析返回地址</p> 
	 * @author 庄佳彬
	 * @param json
	 * @return String
	 */
	private String getFormattedAddress(String json){
		Map<String,Object> map = JSONObject.parseObject(json);
		return ((Map)map.get("result")).get("formatted_address").toString();
	}
	
	
	
	
	/**
	 * <p>Title: getPointLatLng</p>
	 * <p>Description:解析返回坐标</p> 
	 * @author 庄佳彬
	 * @param json
	 * @return PointLatLng
	 */
	private List<GeoPoint> getPointLatLng(String json) {
		List<GeoPoint> ps = new ArrayList<GeoPoint>();
		Map<String,Object> map = JSONObject.parseObject(json);
		if("0".equals(map.get("status").toString())){
			List<Map> list = (List) map.get("result");
			for (Map result : list) {
				GeoPoint p = new GeoPoint();
				Double lng = (Double) result.get("x");
				Double lat = (Double) result.get("y");
				p.setLan(lat);
				p.setLon(lng);
				ps.add(p);
			}
		}
		return ps;
	}

    /**
     * 测距，两点测距
     *
     * @param p1 the p 1
     * @param p2 the p 2
     * @return the double
     * @Auth Will
     * @Date 2016 -08-31 09:36:53
     */
    @Override
	public double distance(GeoPoint p1, GeoPoint p2) {
		List<Double> distances = distances(p1,p2);
		if(CollectionUtils.isEmpty(distances)){
			return distances.get(0);
		}else{
			return 0;
		}
	}


    /**
     * 多点测距，两两测距
     *
     * @param ps the ps
     * @return the list
     * @Auth Will
     * @Date 2016 -08-31 09:37:13
     */
    public List<Double> distances(GeoPoint... ps) {
		try {
			StringBuffer sb = new StringBuffer();
			Map<String, String> paramsMap = new LinkedHashMap<String, String>();
			for (GeoPoint pointLatLng : ps) {
				sb.append(pointLatLng.getLon()).append(",").append(pointLatLng.getLan());
				sb.append(";");
			}
			paramsMap.put("waypoints", sb.substring(0, sb.length() - 1));
			paramsMap.put("output", "json");
			paramsMap.put("ak", AK);
			String queryStr = toQueryString(paramsMap);
			String wholeStr = new String("/telematics/v3/distance?" + queryStr + SK);
			String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
			String SN = MD5(tempStr);
			paramsMap.put("sn", SN);
			URL url = new URL("http://api.map.baidu.com/telematics/v3/distance?" + toQueryString(paramsMap));
			URLConnection connection = url.openConnection();

			InputStream in = connection.getInputStream();
			String resultJson = null;
			byte[] tt = new byte[in.available()];
			while (in.read(tt, 0, tt.length) != -1) {
				resultJson = new String(tt, "utf-8");
			}
			return getDistances(resultJson);
		} catch (UnsupportedEncodingException e) {
			log.error("计算两点距离异常", e);
		} catch (MalformedURLException e) {
			log.error("计算两点距离异常", e);
		} catch (IOException e) {
			log.error("计算两点距离异常", e);
		}
		return null;
	}

	private List<Double> getDistances(String resultJson) {
		JSONObject jo = JSON.parseObject(resultJson);
//        log.error(jo.toString());
		List<BigDecimal> bds = (List<BigDecimal>) jo.get("results");
        List<Double> ds = new ArrayList<>();
        for (BigDecimal bd:
             bds) {
            ds.add(bd.doubleValue());
        }
        return ds;
    }

	// 对Map内所有value作utf8编码，拼接返回结果   此方法由百度例子自带无需更改
	private String toQueryString(Map<?, ?> data)
			throws UnsupportedEncodingException {
		StringBuffer queryString = new StringBuffer();
		for (Entry<?, ?> pair : data.entrySet()) {
			queryString.append(pair.getKey() + "=");
			queryString.append(URLEncoder.encode((String) pair.getValue(),
					"UTF-8") + "&");
		}
		if (queryString.length() > 0) {
			queryString.deleteCharAt(queryString.length() - 1);
		}
		return queryString.toString();
	}

	// 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制 此方法由百度例子自带无需更改
	private String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}
	
	public static void main(String[] args) {
		Object d = getBaiduUtil().distances(new GeoPoint(29.490295,106.486654), new GeoPoint(29.615467,106.581515),new GeoPoint(30.490295,106.486654));
		System.out.println(d);
	}
}
