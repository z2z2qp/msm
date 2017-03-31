/**
 * @Title MapUtil.java
 * @Package com.chuangyuan.util.map.baidu
 * @Description
 * @Author 庄佳彬
 * @Date 2016年5月30日
 * @Version v1.0
 */
package com.will.utils.map;


/**
 * <li>ClassName: MapUtil</li>
 * <li>Description:</li>
 * <li>Change:</li>
 * <li>Date: 2016年5月30日 上午10:47:29</li>
 * @author 庄佳彬
 */
public abstract class MapUtil {
	
	
	/**
	 * 地球半径单位m
	 */
	protected final static double EARTH_RADIUS = 6370856.0;
	
	/**
	 * 
	 * <p>Title: rad</p>
	 * <p>Description:度转弧度</p> 
	 * @author 庄佳彬
	 * @Date 2016年5月30日
	 * @param d
	 * @return double
	 */
	protected double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 
	 * <p>Title: distance</p>
	 * <p>Description:两点距离</p> 
	 * @author 庄佳彬
	 * @Date 2016年5月30日
	 * @param p1
	 * @param p2
	 * @return double
	 */
	public double distance(GeoPoint p1,GeoPoint p2) {
		double radLat1 = rad(p1.getLan());
		double radLat2 = rad(p2.getLan());
		double a = radLat1 - radLat2;
		double b = rad(p1.getLon()) - rad(p2.getLon());
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}
	
	/**
	 * 
	 * <p>Title: location2Adress</p>
	 * <p>Description:坐标转地址</p> 
	 * @author 庄佳彬
	 * @Date 2016年5月30日
	 * @param pointLatLng
	 * @return String
	 */
	public abstract String location2Adress(GeoPoint pointLatLng);
}
