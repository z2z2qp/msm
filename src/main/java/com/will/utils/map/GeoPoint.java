package com.will.utils.map;

/**
 * 经纬度坐标类 支持double和int 输入输出
 * 
 * @author Administrator zwc
 * 
 */
public class GeoPoint
{
	public static String FROM_GEO="84";
	public static String TO_GEO="bd";
	
	public GeoPoint()
	{
	}
	
	public GeoPoint(GeoPoint g)
	{
		if(g==null)
			return;
		
		this.lan=g.getLan();
		this.lon=g.getLon();
		this.direct=g.getDirect();
	}

	public GeoPoint(double lan, double lon)
	{
		this.lan = lan;
		this.lon = lon;
		
	}
	

	public GeoPoint(double lan, double lon, int direct)
	{
		this.lan = lan;
		this.lon = lon;
		this.direct = direct;
		
	}
	
	public GeoPoint(double lan,double lon,boolean transfer)
	{
		this.lan = lan;
		this.lon = lon;
		

	}


	double lan;
	double lon;
	int direct;

	public int getDirect()
	{
		return (direct+360)%360;

	}
	public void setDirect(int direct)
	{
		this.direct = direct;
	}
	public double getLan()
	{
		return lan;
	}

	public void setLan(double lan)
	{
		this.lan = lan;
	}

	public double getLon()
	{
		return lon;
	}

	public void setLon(double lon)
	{
		this.lon = lon;
	}

	public void setLon1E6(int lon)
	{
		this.lon = ((double) lon) / 1e6;
	}

	public int getLan1E6()
	{
		return (int) (lan * 1e6);
	}

	public void setLan1E6(double lan)
	{
		this.lan = ((double) lan) / 1e6;
	}

	public int getLon1E6()
	{
		return (int) (lon * 1e6);
	}
	


}
